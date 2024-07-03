package com.hana.hanalink.common.firebase;

import com.google.firebase.messaging.*;
import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.member.service.MemberDetailsService;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FirebaseFcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final TeamRepository teamRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final MemberDetailsService memberDetailsService;
    private final TeamMemberRepository teamMemberRepository;

    /*모임 구독하기*/
    public void subscribeToTopic(String token, String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                Collections.singletonList(token), topic);
    }

    /*모임 구독취소*/
    public void unSubscribeFromTopic(String token, String topic) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Collections.singletonList(token), topic);
    }


    /*모임 가입된 사람에게 알림*/
    /*모임 승인요청시 총무 알림*/
    /*큐알 결제시 총무 알림*/
    public void sendFcmTeamOfAlarmType(String token,String title,String body,Team team,Member member) {
        FcmMessageReq req = FcmMessageReq.builder()
                .title(title)
                .body(body)
                .image(null)
                .type(AlarmType.TEAM)
                .member(member)
                .team(team)
                .build();

        Message message = makeFcmMessage(token,title, body,null);
        this.sendMessage(message,req);
    }

    /*level 업그레이드시 알림*/
    /*설문조사 요청시 모임원 알림*/
    /*일정 개설시 모임원 알림*/
    public void sendTopicMessageWithImage(Long teamId, String title, String body,AlarmType type,boolean isUseImg)  {
        Member member = memberRepository.findByPhone(memberDetailsService.getCurrentUserDetails().getUsername()).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        if (AlarmType.SURVEY.equals(type)) {
            List<TeamMember> teamMembers = teamMemberRepository.findTeamMembersByTeam_TeamIdAndRole(teamId, TeamMemberRole.REGULAR);
            for(TeamMember teamMember:teamMembers) {
                FcmMessageReq req = FcmMessageReq.builder()
                        .title(title)
                        .body(body)
                        .image(isUseImg ? team.getThumbNail() : null)
                        .team(teamMember.getTeam())
                        .member(teamMember.getMember())
                        .type(type)
                        .build();
                Message message = makeFcmMessageForTopic(teamId.toString(), title, body, null);
                this.sendMessage(message, req);
            }
        } else {
            FcmMessageReq req = FcmMessageReq.builder()
                    .title(title)
                    .body(body)
                    .image(isUseImg? team.getThumbNail() :null)
                    .team(team)
                    .member(member)
                    .type(type)
                    .build();
            Message message= makeFcmMessageForTopic(teamId.toString(),title,body,null);
            this.sendMessage(message,req);
        }
    }

    public void sendMessage(Message message,FcmMessageReq req) {

        firebaseMessaging.sendAsync(message);

        Alarm alarm = Alarm.builder()
                .title(req.title())
                .body(req.body())
                .isSeen(false)
                .type(req.type())
                .member(req.member())
                .team(req.team())
                .build();
        alarmRepository.save(alarm);

    }

    private Message makeFcmMessage(String token, String title,String body, String image) {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setToken(token).setNotification(notification).build();
        return msg;
    }

    private Message makeFcmMessageForTopic(String topic, String title,String body, String image) {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).putData("teamId",topic).build();
        return msg;
    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return firebaseMessaging.sendEachForMulticast(message);
    }

}
