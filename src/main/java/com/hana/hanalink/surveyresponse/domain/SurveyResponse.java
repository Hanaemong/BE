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

    @Column(name = "total_score", nullable = false)
    private Float totalScore;

    @Column(name = "survey_cnt", nullable = false)
    private Integer surveyCnt;


    public Float sumTotalScore(Float score){
        totalScore += score;
        return totalScore;
    }

    public int addSurveyCnt(){
         surveyCnt+=1;
         return surveyCnt;
    }
}
