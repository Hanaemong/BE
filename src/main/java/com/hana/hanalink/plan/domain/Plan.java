package com.hana.hanalink.plan.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "plan_date", nullable = false)
    private LocalDateTime planDate;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "plan_img")
    private String planImg;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
