package com.hana.hanalink.plan.service;

import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.common.service.FirebaseFcmService;
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

    private final FirebaseFcmService firebaseFcmService;

    public long postPlan(Long teamId, PlanPostReq planPostReq,String image){
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Long planId = planRepository.save(planPostReq.toEntity(team,image)).getPlanId();

        if (planId != null) {
            firebaseFcmService.sendTopicMessage(teamId,teamId.toString(),team.getTeamName()+"모임 일정 개설!!",planPostReq.planName()+"일정이 개설되었어요 ~\n 지금 바로 확인해보세요.");
            return  planId;
        }

        throw new EntityNotFoundException();
    }
}
