package com.hana.hanalink.alarm.domain;

import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public AlarmRes toDto() {
        return AlarmRes.builder()
                .title(title)
                .body(body)
                .createdAt(this.getCreatedAt())
                .build();
    }
}
