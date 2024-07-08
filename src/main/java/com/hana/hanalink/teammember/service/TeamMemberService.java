package com.hana.hanalink.teammember.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.member.exception.MemberNotFoundException;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.exception.TeamNotFoundException;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.dto.MyTeamMemberRes;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.exception.TeamMemberNotFoundException;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final FirebaseFcmService firebaseFcmService;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<TeamMemberRes> getTeamMembers(Long teamId) {
        return teamMemberRepository.findTeamMemberByTeam_TeamId(teamId).stream().map(teamMember -> teamMember.toDto(teamMember.getMember().getGender(),teamMember.getMember().getProfile(),teamMember.getNickname())
                ).toList();
    }

    public MyTeamMemberRes getMyNickname(MemberDetails memberDetails, Long teamId) {
        Member member = memberRepository.findByPhone(memberDetails.getUsername()).orElseThrow(MemberNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team).orElseThrow(TeamMemberNotFoundException::new);

        return new MyTeamMemberRes(teamMember.getNickname(),member.getProfile(),teamMember.getRole());
    }

    @Transactional
    public void deleteTeamMember(Long teamMemberId, String type, MemberDetails memberDetails) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElseThrow(TeamMemberNotFoundException::new);
        Long id = teamMemberId;
        switch (type) {
            /*탈퇴 ! 여기서는 teamMemberId가 teamId로 쓰임*/
            case "LEAVE":
                Member member = memberRepository.findByPhone(memberDetails.getUsername()).orElseThrow(MemberNotFoundException::new);
                TeamMember myTeamMember = teamMemberRepository.findByMemberAndTeam_TeamId(member,id).orElseThrow(TeamMemberNotFoundException::new);
                firebaseFcmService.sendFcmTeamOfAlarmType(myTeamMember.getMember().getFcmToken(),"모임 탈퇴 알림🥲",myTeamMember.getTeam().getTeamName()+"모임에 탈퇴되었습니다.",myTeamMember.getTeam(),myTeamMember.getMember());
                id = myTeamMember.getTeamMemberId();
            /*거절*/
            case "DENY":
                firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 거절 알림🥺",teamMember.getTeam().getTeamName()+"모임가입이 거절되었습니다.",teamMember.getTeam(),teamMember.getMember());
            /*내보내기*/
            case "REJECT":
                firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 강퇴 알림☹️",teamMember.getTeam().getTeamName()+"모임에 내보내기되었습니다.",teamMember.getTeam(),teamMember.getMember());

        }
        teamMemberRepository.deleteById(id);
    }

    @Transactional
    public void approveTeamMember(Long teamMemberId) throws FirebaseMessagingException {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElseThrow(TeamMemberNotFoundException::new);
        teamMember.changeRole(TeamMemberRole.REGULAR);
        teamMemberRepository.save(teamMember);

        /* fcm 모임 가입 허락된 모임원에게 알림 발송*/
        firebaseFcmService.subscribeToTopic(teamMember.getMember().getFcmToken(),teamMember.getTeam().getTeamId().toString());
        firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 수락 승인 완료!🥳",teamMember.getTeam().getTeamName()+"에 가입이 완료되었어요 ~!",teamMember.getTeam(),teamMember.getMember());
    }

}
