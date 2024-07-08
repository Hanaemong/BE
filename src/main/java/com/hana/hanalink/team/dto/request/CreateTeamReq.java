package com.hana.hanalink.team.dto.request;

import com.hana.hanalink.meetingaccount.domain.MeetingAccount;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.surveyresponse.domain.SurveyResponse;
import com.hana.hanalink.team.domain.Team;

public record CreateTeamReq(String teamName, String teamDesc, Integer capacity, String category, String nickname) {
    public static Team toEntity(CreateTeamReq req, SiGunGu siGunGu, SurveyResponse surveyResponse, MeetingAccount meetingAccount, String thumbNail) {

        return Team.builder()
                .teamName(req.teamName())
                .teamDesc(req.teamDesc())
                .capacity(req.capacity())
                .category(req.category())
                .thumbNail(thumbNail)
                .banner("https://hanalinkbucket.s3.ap-northeast-2.amazonaws.com/banner.png")
                .score(0.5F)
                .siGunGu(siGunGu)
                .surveyResponse(surveyResponse)
                .meetingAccount(meetingAccount)
                .build();
    }

}
