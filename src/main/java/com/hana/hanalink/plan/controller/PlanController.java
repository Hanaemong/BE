package com.hana.hanalink.plan.controller;

import com.hana.hanalink.plan.dto.PlanPostReq;
import com.hana.hanalink.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlanController {

    private final PlanService planService;
    @PostMapping("/plan/{teamId}")
    public Long postPlan(@PathVariable("teamId") Long teamId, @RequestBody PlanPostReq planPostReq){
        return planService.postPlan(teamId,planPostReq);
    }
}