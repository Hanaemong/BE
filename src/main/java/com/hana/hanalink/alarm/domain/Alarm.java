package com.hana.hanalink.alarm.domain;

import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
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

    @Column(name = "image")
    private String image;

    @Column(name= "is_seen", nullable = false)
    private Boolean isSeen = false;

    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false)
    private AlarmType type;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id",nullable = false)
    private Team team;

    public AlarmRes toDto() {
        return AlarmRes.builder()
                .title(title)
                .body(body)
                .image(image)
                .isSeen(isSeen)
                .type(type)
                .teamId(team.getTeamId())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public void setIsSeen(){
        this.isSeen = true;
    }
}
