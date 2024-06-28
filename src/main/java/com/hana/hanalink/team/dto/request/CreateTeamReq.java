package com.hana.hanalink.team.dto.request;

import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.surveyresponse.domain.SurveyResponse;
import com.hana.hanalink.team.domain.Team;

public record CreateTeamReq(String teamName, String teamDesc, Integer capacity, String category, String thumbNail) {
    public static Team toEntity(CreateTeamReq req, SiGunGu siGunGu, SurveyResponse surveyResponse) {
        return Team.builder()
                .teamName(req.teamName())
                .teamDesc(req.teamDesc())
                .capacity(req.capacity())
                .category(req.category())
                .thumbNail(req.thumbNail())
                .score(0.5F)
                .SiGunGu(siGunGu)
                .surveyResponse(surveyResponse)
                .build();
    }
}
