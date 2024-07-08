package com.hana.hanalink.team.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.account.util.AccountNumberGenerator;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.meetingacount.domain.MeetingAccount;
import com.hana.hanalink.meetingacount.repository.MeetingAccountRepository;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.exception.MemberNotFoundException;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.surveyresponse.domain.SurveyResponse;
import com.hana.hanalink.surveyresponse.repository.SurveyResponseRepository;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.dto.request.CreateTeamReq;
import com.hana.hanalink.team.dto.request.JoinTeamReq;
import com.hana.hanalink.team.dto.response.CreateTeamRes;
import com.hana.hanalink.team.dto.response.DetailTeamRes;
import com.hana.hanalink.team.dto.response.TeamRes;
import com.hana.hanalink.team.exception.TeamNotFoundException;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.exception.NicknameDuplException;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final MeetingAccountRepository meetingAccountRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final FirebaseFcmService firebaseFcmService;

    @Transactional
    public CreateTeamRes createTeam(String phone, CreateTeamReq req, String thumbNail) {
        if (nicknameDupl(req.nickname()))
            throw new NicknameDuplException();

        Member member = getMember(phone);
        Account account = accountRepository.findAccountByMember_MemberId(member.getMemberId());
        MeetingAccount meetingAccount = MeetingAccount.builder()
                .meetingAccountNumber(AccountNumberGenerator.generateAccountNumber())
                .account(account)
                .build();
        SurveyResponse surveyResponse = SurveyResponse.builder()
                .surveyCnt(0)
                .totalScore(0F)
                .build();
        meetingAccountRepository.save(meetingAccount);
        surveyResponseRepository.save(surveyResponse);
        Team team = CreateTeamReq.toEntity(req, member.getSiGunGu(), surveyResponse, meetingAccount, thumbNail);
        teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .nickname(req.nickname())
                .role(TeamMemberRole.CHAIR)
                .member(member)
                .team(team)
                .build();
        teamMemberRepository.save(teamMember);

        return new CreateTeamRes();
    }

    @Transactional
    public void updateBanner(Long teamId, String banner) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        team.setBanner(banner);
        teamRepository.save(team);
    }

    @Transactional
    public void joinTeam(String phone, Long teamId, JoinTeamReq req) {
        if (nicknameDupl(req.nickname()))
            throw new NicknameDuplException();

        Member member = getMember(phone);
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        TeamMember teamMember = TeamMember.builder()
                .nickname(req.nickname())
                .role(TeamMemberRole.PENDING)
                .member(member)
                .team(team)
                .build();
        teamMemberRepository.save(teamMember);

        /*fcm 모임 총무한테 가입요청 알림 발송*/
        TeamMember chair = teamMemberRepository.findTeamMemberByTeam_TeamIdAndRole(teamId,TeamMemberRole.CHAIR);
        firebaseFcmService.sendFcmTeamOfAlarmType(chair.getMember().getFcmToken(),"모임 수락 요청 알림",member.getName()+"님이 "+team.getTeamName()+" 모임 승인 요청하였습니다.",chair.getTeam(),chair.getMember());
    }

    public List<TeamRes> getTeamList(String phone) {
        Member member = getMember(phone);
        List<Team> teamList = teamRepository.findBySiGunGuOrderByScoreDesc(member.getSiGunGu());

        return teamList.stream()
                .map(this::buildTeamRes)
                .toList();
    }

    public List<TeamRes> getCategoryTeamList(String phone, String category) {
        Member member = getMember(phone);
        List<Team> teamList = teamRepository.findBySiGunGuAndCategory(member.getSiGunGu(), category);

        return teamList.stream()
                .map(this::buildTeamRes)
                .toList();
    }

    public List<TeamRes> getSearchTeamList(String phone, String teamName) {
        Member member = getMember(phone);
        List<Team> teamList = teamRepository.findBySiGunGuAndTeamNameContaining(member.getSiGunGu(), teamName);

        return teamList.stream()
                .map(this::buildTeamRes)
                .toList();
    }

    public List<TeamRes> getMyTeamList(String phone) {
        Member member = getMember(phone);
        List<TeamMember> teamMembers = teamMemberRepository.findByMember(member);

        return teamMembers.stream()
                .map(TeamMember::getTeam)
                .distinct()
                .map(this::buildTeamRes)
                .toList();
    }

    public DetailTeamRes getDetailTeam(String phone, Long teamId) {
        Member member = getMember(phone);
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        // 팀 멤버 정보 조회
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByMemberAndTeam(member, team);
        TeamMemberRole role = teamMemberOptional.map(TeamMember::getRole).orElse(null);

        return new DetailTeamRes(team, role, teamMemberOptional.isPresent() ? teamMemberOptional.get().getNickname() : "");    }


    private Member getMember(String phone) {
        return memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
    }

    private TeamRes buildTeamRes(Team team) {
        return TeamRes.builder()
                .teamId(team.getTeamId())
                .siGunGu(team.getSiGunGu().getSiGunGu())
                .teamName(team.getTeamName())
                .category(team.getCategory())
                .score(team.getScore())
                .thumbNail(team.getThumbNail())
                .memberCnt(teamMemberRepository.countByTeamAndRoleNot(team,TeamMemberRole.PENDING))
                .build();
    }

    public boolean nicknameDupl(String nickName) {
        return teamMemberRepository.existsByNickname(nickName);
    }




}
