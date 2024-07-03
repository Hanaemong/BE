package com.hana.hanalink.survey.controller;

import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SurveyController {

    private final FirebaseFcmService firebaseFcmService;

    @GetMapping("/survey/{teamId}")
    public SuccessResponse<Void> requestSurvey(@PathVariable("teamId") Long teamId) {
        firebaseFcmService.sendTopicMessageWithImage(teamId,"설문조사 요청"," 모임은 즐거우셨나요?\n지금 당장 설문조사에 참여해 내 모임의 등급을\n올려보세요 ~!!", AlarmType.SURVEY,true);
        return SuccessResponse.successWithNoData();
    }
}
