package com.hana.hanalink.alarm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.hanalink.common.config.SecurityConfig;
import com.hana.hanalink.common.jwt.JwtUtil;
import com.hana.hanalink.member.service.MemberDetailsService;
import com.hana.hanalink.survey.service.SurveyService;
import com.hana.hanalink.surveyresponse.controller.SurveyResponseController;
import com.hana.hanalink.surveyresponse.dto.request.SurveyRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SurveyResponseController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
public class SurveyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private MemberDetailsService memberDetailsService;


    @MockBean
    private SurveyService surveyService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser
    @DisplayName("설문 응답 제출하기")
    public void testSubmitSurvey() throws Exception {
        // Given
        Long teamId = 1L;
        SurveyRes surveyRes = SurveyRes.builder().score(4.5f).build();
        Long expectedTeamId = 1L;

        Mockito.when(surveyService.submitSurvey(Mockito.eq(teamId), Mockito.any(SurveyRes.class)))
                .thenReturn(expectedTeamId);

        // When & Then
        mockMvc.perform(post("/api/v1/survey/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyRes))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.type").value(""))
                .andExpect(jsonPath("$.data").value(expectedTeamId.intValue()));

    }

}
