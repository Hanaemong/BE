package com.hana.hanalink.team.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.surveyresponse.domain.SurveyResponse;
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

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "team_desc", nullable = false)
    private String teamDesc;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "meeting_account", nullable = false)
    private String meetingAccount;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "banner")
    private String banner;

    @OneToOne
    @JoinColumn(name = "si_gun_gu_id")
    private SiGunGu SiGunGu;

    @OneToOne
    @JoinColumn(name = "survey_response")
    private SurveyResponse surveyResponse;
}
