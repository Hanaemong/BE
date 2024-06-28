package com.hana.hanalink.teammember.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
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

    @GetMapping("/teamMember/{teamId}")
    public SuccessResponse<List<TeamMemberRes>> getTeamMemberList(@PathVariable("teamId") Long teamId, @AuthenticationPrincipal MemberDetails member) {
        return SuccessResponse.success(teamMemberService.getTeamMembers(teamId,member.getMemberGender(),member.getMemberProfile()));
    }

    @DeleteMapping("/teamMember/{teamMemberId}")
    public SuccessResponse<Long> deleteTeamMember(@PathVariable("teamMemberId") Long teamMemberId) {
        teamMemberService.deleteTeamMember(teamMemberId);
        return SuccessResponse.successWithNoData();

    }
}
