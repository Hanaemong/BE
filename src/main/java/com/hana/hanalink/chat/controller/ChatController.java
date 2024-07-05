package com.hana.hanalink.chat.controller;

import com.hana.hanalink.chat.dto.ChatDto;
import com.hana.hanalink.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatService chatService;

    @MessageMapping("/send")
    public Mono<Void> message(ChatDto chatDto) {
        simpMessageSendingOperations.convertAndSend("/sub/room/" + chatDto.getRoomId(), chatDto);
        return chatService.saveChat(chatDto.toEntity()).then();
    }
}