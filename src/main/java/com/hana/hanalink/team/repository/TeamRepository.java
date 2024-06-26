package com.hana.hanalink.team.repository;

import com.hana.hanalink.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
