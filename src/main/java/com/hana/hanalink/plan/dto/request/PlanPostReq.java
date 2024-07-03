package com.hana.hanalink.plan.dto.request;

import com.hana.hanalink.plan.domain.Plan;
import com.hana.hanalink.team.domain.Team;

import java.time.LocalDateTime;

public record PlanPostReq (String planName, LocalDateTime planDate, String planPlace, long planCost ){

    public Plan toEntity(Team team,String image){
        return Plan.builder()
                .planName(planName)
                .place(planPlace)
                .planDate(planDate)
                .cost(planCost)
                .planImg(image)
                .team(team)
                .build();
    }
}
