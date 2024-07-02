package com.hana.hanalink.alarm.controller;

import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.service.AlarmService;
import com.hana.hanalink.common.jwt.JwtUtil;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.member.service.MemberDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AlarmController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AlarmRestControllerTest {

    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private MemberDetailsService memberDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

//    @BeforeEach
    public void setUp() {

        MemberDetails memberDetails = new MemberDetails(Member.builder().memberId(1L).build());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities()));

    }


    @Test
    @WithMockUser
    @DisplayName("알람 목록 가져오기")
    public void testGetAlarmList() throws Exception {

        setUp();

        //given
        Long memberId = 1L;
        List<AlarmRes> alarms = List.of(
                new AlarmRes("hi", "hihi", null,null,false, AlarmType.SURVEY),
                new AlarmRes("hi", "hihi", null,null,false, AlarmType.SURVEY)
        );
        when(alarmService.getAlarmList(memberId)).thenReturn(alarms);

        //when, then
        mockMvc.perform(get("/api/v1/alarm").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(alarms.size()));

    }

    @Test
    @DisplayName("인증되지 않은 사용자 접근 테스트")
    public void testUnauthorizedAccess() throws Exception{

        mockMvc.perform(get("/api/v1/alarm"))
                .andExpect(status().isUnauthorized());
    }



}
