package com.hana.hanalink.chat.repository;

import com.hana.hanalink.chat.domain.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findByRoomId(String roomId);
    Mono<Boolean> existsByNickname(String nickname);
}
