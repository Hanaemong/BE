package com.hana.hanalink.survey.service;

import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.surveyresponse.dto.request.SurveyRes;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final TeamRepository teamRepository;

    public Long submitSurvey(Long teamId, SurveyRes surveyRes){
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        team.getSurveyResponse().addSurveyCnt();
        team.getSurveyResponse().sumTotalScore(surveyRes.score());
        return teamRepository.save(team).getTeamId();
    }

}
