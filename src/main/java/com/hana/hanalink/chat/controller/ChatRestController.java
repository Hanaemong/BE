package com.hana.hanalink.chat.controller;

import com.hana.hanalink.chat.domain.Chat;
import com.hana.hanalink.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/dupl")
    public Mono<Boolean> checkNicknameExists(@RequestParam("nickname") String nickname) {
        return chatService.checkNicknameDupl(nickname);
    }
}
