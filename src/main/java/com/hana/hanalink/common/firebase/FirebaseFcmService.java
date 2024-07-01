package com.hana.hanalink.common.firebase;

import com.google.firebase.messaging.*;
import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.member.domain.Member;
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

    public void subscribeToTopic(String token, String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                Collections.singletonList(token), topic);
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
    }

    public void sendTargetMessage(String targetToken, String title, String body)  {
        this.sendTargetMessage(targetToken, title, body, null);
    }
    public void sendTargetMessage(String targetToken, String title, String body, String image)  {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setToken(targetToken).setNotification(notification).build();
        sendMessage(msg,title,body);
    }

    public void sendTopicMessageWithLogoImage(Long teamId,String topic, String title, String body)  {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(null).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).putData("teamId",topic).build();
        sendMessage(msg,title,body);
    }
    public void sendTopicMessageWithTeamImage(Long teamId,String topic, String title, String body) {
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(team.getThumbNail()).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).putData("teamId",topic).build();
        sendMessage(msg,title,body);
    }

    public void sendMessage(Message message,String title, String body) {

        if (firebaseMessaging.sendAsync(message).isDone()) {
            Alarm alarm = Alarm.builder()
                    .title(title)
                    .body(body)
                    .member(new Member())
                    .build();
            alarmRepository.save(alarm);
        };
    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return firebaseMessaging.sendEachForMulticast(message);
    }

}
