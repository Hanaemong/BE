package com.hana.hanalink.team.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(name = "teamName", nullable = false)
    private String teamName;

    @Column(name = "teamDesc", nullable = false)
    private String teamDesc;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "meetingAcount", nullable = false)
    private String meetingAcount;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "thumbNail", nullable = false)
    private String thumbNail;

    @Column(name = "banner", nullable = false)
    private String banner;
}
