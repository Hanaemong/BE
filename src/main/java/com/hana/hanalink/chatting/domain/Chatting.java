package com.hana.hanalink.chatting.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Alarm")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chatting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(name = "content", nullable = false)
    private String content;
}
