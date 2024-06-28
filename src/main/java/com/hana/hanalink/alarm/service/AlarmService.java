package com.hana.hanalink.alarm.service;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    public List<AlarmRes>  getAlarmList(Long memberId) {

        List<AlarmRes> alarms = alarmRepository.findAlarmByMember_MemberId(memberId).stream().map(Alarm::toDto).toList();

        if (alarms.isEmpty()) {
            return Collections.emptyList();
        }
        return alarms;
    }
}
