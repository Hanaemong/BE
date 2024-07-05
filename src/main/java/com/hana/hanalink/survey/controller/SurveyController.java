package com.hana.hanalink.survey.controller;

import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SurveyController {

    private final FirebaseFcmService firebaseFcmService;
    private final PlanService planService;

    @PostMapping("/survey/request/{teamId}/{planId}")
    public SuccessResponse<Void> requestSurvey(@PathVariable("teamId") Long teamId,@PathVariable("planId") Long planId) {
        firebaseFcmService.sendTopicMessageWithImage(teamId,"설문조사 요청 알림📪","모임은 즐거우셨나요?\n지금 당장 설문조사에 참여해 내 모임의 등급을\n올려보세요 ~!", AlarmType.SURVEY,true);
        planService.setIsSurveyed(planId);
        return SuccessResponse.successWithNoData();
    }
}
