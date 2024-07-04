package com.hana.hanalink.survey.service;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.surveyresponse.dto.request.SurveyRes;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final TeamRepository teamRepository;
    private final FirebaseFcmService firebaseFcmService;
    private final AlarmRepository alarmRepository;

    public Long submitSurvey(Long teamId,Long memberId, SurveyRes surveyRes){
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        float beforeScore = team.getScore();

        int totalCount = team.getSurveyResponse().addSurveyCnt();
        float totalScore = team.getSurveyResponse().sumTotalScore(surveyRes.score());
        float curScore = Math.round(((totalScore/totalCount)/20*5)*10)/10.0f;
        team.setScore(curScore);

        /*설문응답하면 설문조사 알람 확인 처리*/
        List<Alarm> alarms = alarmRepository.findAlarmsByMember_MemberIdAndTypeAndTeam_TeamId(memberId, AlarmType.SURVEY,teamId);

        for(Alarm alarm:alarms) {
            alarm.setIsSeen();
            alarmRepository.save(alarm);
        }

        /* fcm 하나체인 등급에 따른 업그레이드 알림*/
        checkUpgradeLevel(beforeScore,curScore,teamId);

        return teamRepository.save(team).getTeamId();
    }


    public void checkUpgradeLevel(float beforeScore,float curScore,Long teamId) {
        int preRoundedScore = (int) Math.floor(beforeScore);
        int roundedScore = (int) Math.floor(curScore);

        if (preRoundedScore != roundedScore) {
            String title = "🔗내 모임 등급 변경\uD83E\uDE77🎉!";
            String message = "";

            switch (roundedScore) {
                case 0:
                    message = "내 모임 등급이 🥉브론즈로 변경되었어요";
                    break;
                case 1:
                    message = "내 모임 등급이 🥈실버로 변경되었어요";
                    break;
                case 2:
                    message = "내 모임 등급이 🥇골드로 변경되었어요";
                    break;
                case 3:
                    message = "내 모임 등급이 💎다이아로 변경되었어요";
                    break;
                case 4:
                case 5:
                    message = "내 모임 등급이 🧬하나 VIP로 변경되었어요";
                    break;
                default:
                    // 만약 예상하지 못한 등급이 들어올 경우 예외 처리
                    throw new IllegalArgumentException("Invalid roundedScore: " + roundedScore);
            }

            firebaseFcmService.sendTopicMessageWithImage(teamId, title, message, AlarmType.LEVEL, false);
        }


    }

}
