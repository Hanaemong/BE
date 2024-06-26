package com.hana.hanalink.surveyresponse.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.survey.service.SurveyService;
import com.hana.hanalink.surveyresponse.dto.request.SurveyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SurveyResponseController {

    private final SurveyService surveyService;

    @PostMapping("/survey/{teamId}")
    public SuccessResponse<Long> submitSurvey(@PathVariable("teamId") Long teamId, @RequestBody SurveyRes surveyRes) {
        return SuccessResponse.success(surveyService.submitSurvey(teamId,surveyRes));
    }
}
