package com.hana.hanalink.chat.service;

import com.hana.hanalink.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    public Mono<Chat> saveChat(Chat chat) {
        return reactiveMongoTemplate.save(chat);
    }

}
