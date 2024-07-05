package com.hana.hanalink.chat.controller;

import com.hana.hanalink.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChattingController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/send")
    public void message(Message message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }
}