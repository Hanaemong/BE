package com.hana.hanalink.plan.service;

import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.plan.dto.PlanPostReq;
import com.hana.hanalink.plan.repository.PlanRepository;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;
    private final TeamRepository teamRepository;

    public long postPlan(Long teamId, PlanPostReq planPostReq){
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        return planRepository.save(planPostReq.toEntity(team)).getPlanId();
    }
}
