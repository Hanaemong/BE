package com.hana.hanalink.teammember.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.meetingacount.service.MeetingAccountService;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.dto.TeamMemberRoleChangeReq;
import com.hana.hanalink.teammember.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teamMember")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;
    private final MeetingAccountService meetingAccountService;

    /*모임원 조회하기*/
    @GetMapping("/{teamId}")
    public SuccessResponse<List<TeamMemberRes>> getTeamMemberList(@PathVariable("teamId") Long teamId) {
        return SuccessResponse.success(teamMemberService.getTeamMembers(teamId));
    }

    /*모임원 내보내기*/
    @DeleteMapping("/{teamMemberId}")
    public SuccessResponse<Long> deleteTeamMember(@PathVariable("teamMemberId") Long teamMemberId, @RequestParam("type") String type, @AuthenticationPrincipal MemberDetails member) {
        teamMemberService.deleteTeamMember(teamMemberId,type,member);
        return SuccessResponse.successWithNoData();
    }

    /*총무 변경하기*/
    @PostMapping("/{teamId}")
    public SuccessResponse<Long> changeTeamChairRole(@PathVariable("teamId") Long teamId, @RequestBody TeamMemberRoleChangeReq teamMemberRoleChangeReq) {
        meetingAccountService.changeMeetingAccount(teamId, teamMemberRoleChangeReq.ToChairId());
        return SuccessResponse.successWithNoData();
    }

    @PutMapping("/{teamMemberId}")
    public SuccessResponse<Void> approveTeamMember(@PathVariable("teamMemberId") Long teamMemberId) throws FirebaseMessagingException {
        teamMemberService.approveTeamMember(teamMemberId);
        return SuccessResponse.successWithNoData();
    }
}
