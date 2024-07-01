package com.hana.hanalink.team.repository;

import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    List<Team> findBySiGunGuOrderByScoreDesc(SiGunGu sigungu);

    List<Team> findBySiGunGuAndCategory(SiGunGu siGunGu, String category);

    List<Team> findBySiGunGuAndTeamNameContaining(SiGunGu siGunGu, String teamName);
}
