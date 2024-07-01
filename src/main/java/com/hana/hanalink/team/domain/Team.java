package com.hana.hanalink.team.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.meetingacount.domain.MeetingAccount;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.surveyresponse.domain.SurveyResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "team")
@Builder
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

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "banner")
    private String banner;

    @OneToOne
    @JoinColumn(name = "meeting_account_id")
    private MeetingAccount meetingAccount;

    @ManyToOne
    @JoinColumn(name = "si_gun_gu_id")
    private SiGunGu siGunGu;

    @OneToOne
    @JoinColumn(name = "survey_response")
    private SurveyResponse surveyResponse;

    public void setScore(float score) {
        this.score = score;
    }
}
