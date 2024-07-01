package com.hana.hanalink.team.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.account.util.AccountNumberGenerator;
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
import com.hana.hanalink.team.dto.response.TeamRes;
import com.hana.hanalink.team.exception.TeamNotFoundException;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final MeetingAccountRepository meetingAccountRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    @Transactional
    public CreateTeamRes createTeam(String phone, CreateTeamReq req) {
        Member member = memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
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
        Team team = CreateTeamReq.toEntity(req, member.getSiGunGu(), surveyResponse, meetingAccount);
        teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .nickname("")
                .role(TeamMemberRole.CHAIR)
                .hello("")
                .member(member)
                .team(team)
                .build();
        teamMemberRepository.save(teamMember);

        return new CreateTeamRes();
    }

    @Transactional
    public void joinTeam(String phone, Long teamId, JoinTeamReq req) {
        Member member = memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        TeamMember teamMember = TeamMember.builder()
                .nickname("")
                .role(TeamMemberRole.PENDING)
                .hello(req.hello())
                .member(member)
                .team(team)
                .build();
        teamMemberRepository.save(teamMember);
    }

    public List<TeamRes> getTeamList(String phone) {
        Member member = memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
        List<Team> teamList = teamRepository.findBySiGunGuOrderByScoreDesc(member.getSiGunGu());

        return teamList.stream()
                .map(team -> TeamRes.builder()
                        .teamId(team.getTeamId())
                        .siGunGu(team.getSiGunGu().getSiGunGu())
                        .teamName(team.getTeamName())
                        .category(team.getCategory())
                        .score(team.getScore())
                        .thumbNail(team.getThumbNail())
                        .memberCnt(teamMemberRepository.countByTeam(team))
                        .build())
                .toList();
    }

    public List<TeamRes> getCategoryTeamList(String phone, String category) {
        Member member = memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
        List<Team> teamList = teamRepository.findBySiGunGuAndCategory(member.getSiGunGu(), category);

        return teamList.stream()
                .map(team -> TeamRes.builder()
                        .teamId(team.getTeamId())
                        .siGunGu(team.getSiGunGu().getSiGunGu())
                        .teamName(team.getTeamName())
                        .category(team.getCategory())
                        .score(team.getScore())
                        .thumbNail(team.getThumbNail())
                        .memberCnt(teamMemberRepository.countByTeam(team))
                        .build())
                .toList();
    }
}
