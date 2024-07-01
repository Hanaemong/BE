package com.hana.hanalink.teammember.repository;

import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findTeamMemberByTeam_TeamId(Long teamId);
    Integer countByTeam(Team team);

    TeamMember findTeamMemberByTeamTeamIdAndRole(Long teamId, TeamMemberRole role);

    List<TeamMember> findByMember(Member member);

}
