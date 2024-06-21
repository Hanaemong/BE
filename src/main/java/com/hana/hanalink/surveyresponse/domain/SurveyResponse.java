package com.hana.hanalink.surveyresponse.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "survey_response")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SurveyResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyResponseId;

    @Column(name = "total_score")
    private Float totalScore;

    @Column(name = "survey_cnt")
    private Integer surveyCnt;
}
