package com.hana.hanalink.common.service;


import com.google.firebase.messaging.*;
import com.hana.hanalink.common.exception.EntityNotFoundException;
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
        sendMessage(msg);
    }

    public void sendTopicMessage(Long teamId,String topic, String title, String body)  {
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        this.sendTopicMessage(topic, team.getTeamName()+title, body, null);
    }
    public void sendTopicMessage(String topic, String title, String body, String image) {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).build();
        sendMessage(msg);
    }

    public void sendMessage(Message message) {
        firebaseMessaging.sendAsync(message);
    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return firebaseMessaging.sendEachForMulticast(message);
    }

}
