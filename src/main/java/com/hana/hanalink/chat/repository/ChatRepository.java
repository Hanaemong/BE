package com.hana.hanalink.chat.repository;

import com.hana.hanalink.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
