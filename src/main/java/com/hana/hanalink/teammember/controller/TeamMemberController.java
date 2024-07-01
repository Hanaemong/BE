package com.hana.hanalink.teammember.controller;

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
@RequestMapping("/api/v1")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;
    private final MeetingAccountService meetingAccountService;

    /*모임원 조회하기*/
    @GetMapping("/teamMember/{teamId}")
    public SuccessResponse<List<TeamMemberRes>> getTeamMemberList(@PathVariable("teamId") Long teamId, @AuthenticationPrincipal MemberDetails member) {
        return SuccessResponse.success(teamMemberService.getTeamMembers(teamId,member.getMemberGender(),member.getMemberProfile()));
    }

    /*모임원 내보내기*/
    @DeleteMapping("/teamMember/{teamMemberId}")
    public SuccessResponse<Long> deleteTeamMember(@PathVariable("teamMemberId") Long teamMemberId) {
        teamMemberService.deleteTeamMember(teamMemberId);
        return SuccessResponse.successWithNoData();
    }

    /*총무 변경하기*/
    @PostMapping("/teamMember")
    public SuccessResponse<Long> changeTeamChairRole(@RequestBody TeamMemberRoleChangeReq teamMemberRoleChangeReq) {
        teamMemberService.changeChairRole(teamMemberRoleChangeReq);
        meetingAccountService.changeMeetingAccount(teamMemberRoleChangeReq.fromChairId(), teamMemberRoleChangeReq.ToChairId());
        return SuccessResponse.successWithNoData();
    }
}
