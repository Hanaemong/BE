package com.hana.hanalink.alarm.service;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import com.hana.hanalink.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class AlarmServiceTest {

    @InjectMocks
    AlarmService alarmService;

    @Mock
    AlarmRepository alarmRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("알림 리스트 조회")
    void getAlarmListServiceTest() {
        //given
        AlarmRes alarmRes = new AlarmRes("hi", "hihi", null);
        Member member = new Member();
        member.setMemberId(1L);
        Alarm alarm = new Alarm(1L,"hi","hihi",member);

        given(alarmRepository.findById(1L)).willReturn(Optional.of(alarm));

        List<AlarmRes> alarmResList = alarmService.getAlarmList(1L);
        assertThat(alarmResList.size()).isEqualTo(1);
    }
}
