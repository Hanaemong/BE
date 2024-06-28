package com.hana.hanalink.teammember.repository;

import com.hana.hanalink.teammember.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findTeamMemberByTeam_TeamId(Long teamId);
    TeamMember findTeamMemberByTeamMemberId(Long memberId);
}
