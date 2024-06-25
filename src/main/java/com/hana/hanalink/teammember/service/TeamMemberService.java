package com.hana.hanalink.teammember.service;

import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public List<TeamMemberRes> getTeamMembers(Long teamId,Long memberId) {

        return teamMemberRepository.findTeamMemberByTeam_TeamId(teamId).stream().map(teamMember -> teamMember.toDto(new Member())
                ).toList();
    }
}
