package com.hana.hanalink.chat.dto;

import com.hana.hanalink.chat.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private String id;
    private String roomId;
    private String type;
    private String nickname;
    private String msg;
    private String time;
    public Chat toEntity() {
        return Chat.builder()
                .roomId(roomId)
                .type(type)
                .nickname(nickname)
                .msg(msg)
                .time(time)
                .build();
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }
}
