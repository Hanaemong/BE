package com.hana.hanalink.teammember.controller;

import com.hana.hanalink.teammember.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

//    @GetMapping("/teamMember/{teamId}")
//    public List<TeamMemberRes> getTeamMemberList(@PathVariable("teamId") Long teamId) {
//        return teamMemberService.getTeamMembers(teamId,memberId);
//    }
}
