package com.hana.hanalink.survey.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "survey")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    @Column(name = "survey_score",nullable = false)
    private Float surveyScore;
    @Column(name = "group_id",nullable = false)
    private Long groupId;
}