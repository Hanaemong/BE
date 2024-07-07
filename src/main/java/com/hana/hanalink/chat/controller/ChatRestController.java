package com.hana.hanalink.chat.controller;

import com.hana.hanalink.chat.domain.Chat;
import com.hana.hanalink.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/{roomId}")
    public Flux<Chat> getChatsByRoomId(@PathVariable String roomId) {
        return chatService.getChatsByRoomId(roomId);
    }

    @GetMapping("/last/{roomId}")
    public Mono<Chat> getLastChatByRoomId(@PathVariable String roomId) {
        return chatService.getLastChatByRoomId(roomId);
    }
}
