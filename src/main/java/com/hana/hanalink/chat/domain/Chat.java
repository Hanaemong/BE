package com.hana.hanalink.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private String type;
    private Long teamMemberId;
    private String sender;
    private String channelId;
    private String data;
    private LocalDateTime time;

}
