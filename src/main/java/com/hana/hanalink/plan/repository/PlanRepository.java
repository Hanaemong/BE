package com.hana.hanalink.plan.repository;

import com.hana.hanalink.plan.domain.Plan;
import com.hana.hanalink.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan,Long> {
    List<Plan> findByTeam(Team team);
}
