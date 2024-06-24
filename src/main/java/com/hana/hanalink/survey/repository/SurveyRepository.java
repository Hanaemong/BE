package com.hana.hanalink.survey.repository;

import com.hana.hanalink.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey,Long> {
}
