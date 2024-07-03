package com.hana.hanalink.teammember.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.dto.TeamMemberRoleChangeReq;
import com.hana.hanalink.teammember.exception.TeamMemberNotFoundException;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final FirebaseFcmService firebaseFcmService;

    @Transactional(readOnly = true)
    public List<TeamMemberRes> getTeamMembers(Long teamId,String memberGender, String memberProfile) {
        return teamMemberRepository.findTeamMemberByTeam_TeamId(teamId).stream().map(teamMember -> teamMember.toDto(memberGender,memberProfile)
                ).toList();
    }

    @Transactional
    public void deleteTeamMember(Long teamMemberId,String type) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElseThrow(TeamMemberNotFoundException::new);
        switch (type) {
            case "LEAVE":
                firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 탈퇴 알림🥲",teamMember.getTeam().getTeamName()+"모임에 탈퇴되었습니다.",teamMember.getTeam(),teamMember.getMember());

            case "DENY":
                firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 거절 알림🥺",teamMember.getTeam().getTeamName()+"모임가입이 거절되었습니다.",teamMember.getTeam(),teamMember.getMember());

            case "REJECT":
                firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 거절 알림🥺",teamMember.getTeam().getTeamName()+"모임가입이 거절되었습니다.",teamMember.getTeam(),teamMember.getMember());

        }
        teamMemberRepository.deleteById(teamMemberId);
    }

    @Transactional
    public void changeChairRole(TeamMemberRoleChangeReq teamMemberRoleChangeReq) {
        TeamMember curChair = teamMemberRepository.findById(teamMemberRoleChangeReq.fromChairId()).orElseThrow(TeamMemberNotFoundException::new);
        curChair.changeRole(TeamMemberRole.REGULAR);
        TeamMember nextChair = teamMemberRepository.findById(teamMemberRoleChangeReq.ToChairId()).orElseThrow(TeamMemberNotFoundException::new);
        nextChair.changeRole(TeamMemberRole.CHAIR);
        teamMemberRepository.save(curChair);
        teamMemberRepository.save(nextChair);
    }

    @Transactional
    public void approveTeamMember(Long teamMemberId) throws FirebaseMessagingException {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElseThrow(TeamMemberNotFoundException::new);
        teamMember.changeRole(TeamMemberRole.REGULAR);
        teamMemberRepository.save(teamMember);

        /* fcm 모임 가입 허락된 모임원에게 알림 발송*/
        firebaseFcmService.subscribeToTopic(teamMember.getMember().getFcmToken(),teamMember.getTeam().getTeamId().toString());
        firebaseFcmService.sendFcmTeamOfAlarmType(teamMember.getMember().getFcmToken(),"모임 수락 승인 완료!!",teamMember.getTeam().getTeamName()+" 모임에 가입이 완료되었어요 ~!!",teamMember.getTeam(),teamMember.getMember());
    }

}
