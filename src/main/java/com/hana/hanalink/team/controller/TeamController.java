package com.hana.hanalink.team.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.team.dto.request.CreateTeamReq;
import com.hana.hanalink.team.dto.request.JoinTeamReq;
import com.hana.hanalink.team.dto.response.CreateTeamRes;
import com.hana.hanalink.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("")
    SuccessResponse<CreateTeamRes> createTeam(@AuthenticationPrincipal MemberDetails member, @RequestBody CreateTeamReq req) {
        return SuccessResponse.success(teamService.createTeam(member.getUsername(), req));
    }

    @PostMapping("/{teamId}")
    SuccessResponse<Void> joinTeam(@AuthenticationPrincipal MemberDetails member,
                                   @PathVariable("teamId") Long teamId,
                                   @RequestBody JoinTeamReq req) {
        teamService.joinTeam(member.getUsername(), teamId, req);
        return SuccessResponse.successWithNoData();
    }
}
