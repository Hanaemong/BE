package com.hana.hanalink.chat.controller;

import com.hana.hanalink.chat.dto.ChatDto;
import com.hana.hanalink.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatService chatService;

    /*
        /pub/send       메시지 발행
        /topic/roomId   구독
     */
    @MessageMapping("/send")
    public Mono<Void> message(@Payload ChatDto chatDto) {
        simpMessageSendingOperations.convertAndSend("/topic/" + chatDto.getRoomId(), chatDto);
        return chatService.saveChat(chatDto.toEntity()).then();
    }



}