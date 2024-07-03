package com.hana.hanalink.teammember.repository;

import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findTeamMemberByTeam_TeamId(Long teamId);
    Integer countByTeamAndRoleNot(Team team, TeamMemberRole role);

    TeamMember findTeamMemberByTeam_TeamIdAndRole(Long teamId, TeamMemberRole role);

    List<TeamMember> findByMember(Member member);

    Optional<TeamMember> findByMemberAndTeam(Member member, Team team);

}
