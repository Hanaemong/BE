package com.hana.hanalink.plan.service;

import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.plan.domain.Plan;
import com.hana.hanalink.plan.dto.request.PlanPostReq;
import com.hana.hanalink.plan.dto.response.PlanRes;
import com.hana.hanalink.plan.exception.PlanNotFoundException;
import com.hana.hanalink.plan.repository.PlanRepository;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.exception.TeamNotFoundException;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;
    private final TeamRepository teamRepository;

    private final FirebaseFcmService firebaseFcmService;

    public long postPlan(Long teamId, PlanPostReq planPostReq,String image){
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        Long planId = planRepository.save(planPostReq.toEntity(team,image)).getPlanId();

        if (planId != null) {
            firebaseFcmService.sendTopicMessageWithImage(teamId,team.getTeamName()+"모임 일정 개설!!",planPostReq.planName()+"일정이 개설되었어요 ~\n 지금 바로 확인해보세요.", AlarmType.PLAN,true);
            return  planId;
        }

        throw new EntityNotFoundException();
    }

    public List<PlanRes> getPlans(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        List<Plan> planList = planRepository.findByTeam(team);

        return planList.stream()
                .map(this::buildPlanRes)
                .toList();
    }

    private PlanRes buildPlanRes(Plan plan) {
        return PlanRes.builder()
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .planDate(plan.getPlanDate())
                .place(plan.getPlace())
                .cost(plan.getCost())
                .planImg(plan.getPlanImg())
                .isSurveyed(plan.isSurveyed())
                .build();
    }

    public void setIsSurveyed(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
        plan.checkIsSurveyed();
        planRepository.save(plan);
    }
}
