package com.hana.hanalink.teammember.controller;

import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @GetMapping("/teamMember/{teamId}")
    public List<TeamMemberRes> getTeamMemberList(@PathVariable("teamId") Long teamId, @AuthenticationPrincipal MemberDetails member) {
        return teamMemberService.getTeamMembers(teamId,member.getMemberGender(),member.getMemberProfile());
    }
}
