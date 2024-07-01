package com.hana.hanalink.teammember.service;

import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import com.hana.hanalink.teammember.dto.TeamMemberRoleChangeReq;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public List<TeamMemberRes> getTeamMembers(Long teamId,String memberGender, String memberProfile) {
        return teamMemberRepository.findTeamMemberByTeam_TeamId(teamId).stream().map(teamMember -> teamMember.toDto(memberGender,memberProfile)
                ).toList();
    }

    public void deleteTeamMember(Long teamMemberId) {
        teamMemberRepository.deleteById(teamMemberId);
    }

    public void changeChairRole(TeamMemberRoleChangeReq teamMemberRoleChangeReq) {
        TeamMember curChair = teamMemberRepository.findById(teamMemberRoleChangeReq.fromChairId()).orElseThrow(EntityNotFoundException::new);
        curChair.changeChairRole(TeamMemberRole.REGULAR);
        TeamMember nextChair = teamMemberRepository.findById(teamMemberRoleChangeReq.ToChairId()).orElseThrow(EntityNotFoundException::new);
        nextChair.changeChairRole(TeamMemberRole.CHAIR);
        teamMemberRepository.save(curChair);
        teamMemberRepository.save(nextChair);
    }

}
