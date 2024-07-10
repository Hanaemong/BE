package com.hana.hanalink.chat.service;

import com.hana.hanalink.chat.domain.Chat;
import com.hana.hanalink.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class ChatService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ChatRepository chatRepository;


    public Mono<Chat> saveChat(Chat chat) {
        return reactiveMongoTemplate.save(chat);
    }

    public Flux<Chat> getChatsByRoomId(String roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    public Mono<Chat> getLastChatByRoomId(String roomId) {
        return chatRepository.findByRoomId(roomId).last();
    }
    public Mono<Boolean> checkNicknameDupl(String nickname) {
        return chatRepository.existsByNickname(nickname);
    }
}
