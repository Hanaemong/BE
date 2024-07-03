package com.hana.hanalink.common.firebase;

import com.google.firebase.messaging.*;
import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.member.service.MemberDetailsService;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FirebaseFcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final TeamRepository teamRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    private final MemberDetailsService memberDetailsService;

    public void subscribeToTopic(String token, String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                Collections.singletonList(token), topic);
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
    }

    public void sendRequestTeamMemberToChair(String token,String title, String body, Long teamId,Member member) {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(null).build();
        Message msg = Message.builder().setToken(token).setNotification(notification).build();
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);

        this.sendMessage(msg,title,body,AlarmType.TEAM,team,member);
    }

    public void sendTargetMessage(String targetToken, String title, String body,Long teamId)  {
        this.sendTargetMessage(targetToken, title, body, null,memberDetailsService.getCurrentUserDetails(),teamId);
    }
    public void sendTargetMessage(String targetToken, String title, String body, String image,MemberDetails memberDetails,Long teamId)  {
        Member member = memberRepository.findByPhone(memberDetails.getPassword()).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setToken(targetToken).setNotification(notification).build();
        sendMessage(msg,title,body,AlarmType.TEAM,team,member);
    }

    public void sendTopicMessageWithLogoImage(Long teamId, String title, String body,AlarmType type)  {
        Member member = memberRepository.findByPhone(memberDetailsService.getCurrentUserDetails().getUsername()).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(null).build();
        Message msg = Message.builder().setTopic(teamId.toString()).setNotification(notification).putData("teamId",teamId.toString()).build();
        sendMessage(msg,title,body,type,team,member);
    }
    public void sendTopicMessageWithTeamImage(Long teamId, String title, String body, AlarmType type) {
        Member member = memberRepository.findByPhone(memberDetailsService.getCurrentUserDetails().getUsername()).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(team.getThumbNail()).build();
        Message msg = Message.builder().setTopic(teamId.toString()).setNotification(notification).putData("teamId",teamId.toString()).build();
        sendMessage(msg,title,body,type,team,member);
    }

    public void sendMessage(Message message, String title, String body, AlarmType type,Team team,Member member) {
        firebaseMessaging.sendAsync(message);
        Alarm alarm = Alarm.builder()
                .title(title)
                .body(body)
                .isSeen(false)
                .type(type)
                .member(member)
                .team(team)
                .build();
        alarmRepository.save(alarm);

    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return firebaseMessaging.sendEachForMulticast(message);
    }

}
