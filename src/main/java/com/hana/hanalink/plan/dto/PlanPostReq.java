package com.hana.hanalink.plan.dto;

import com.hana.hanalink.plan.domain.Plan;
import com.hana.hanalink.team.domain.Team;

import java.time.LocalDateTime;

public record PlanPostReq (String planName, LocalDateTime planDate, String planPlace, long planCost, String planImg ){

    public Plan toEntity(Team team){
        return Plan.builder()
                .planName(planName)
                .place(planPlace)
                .planDate(planDate)
                .cost(planCost)
                .planImg(planImg)
                .team(team)
                .build();
    }
}
