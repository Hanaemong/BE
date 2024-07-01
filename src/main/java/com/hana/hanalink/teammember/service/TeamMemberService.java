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
    public void deleteTeamMember(Long teamMemberId) {
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
        firebaseFcmService.sendTargetMessage(teamMember.getMember().getFcmToken(),"모임 수락 승인 완료!!",teamMember.getTeam().getTeamName()+" 모임에 가입이 완료되었어요 ~!!");
    }

}
