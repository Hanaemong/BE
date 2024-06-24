package com.hana.hanalink.alarm.service;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public List<AlarmRes>  getAlarmList(Long memberId) {
        return alarmRepository.findAlarmByMember_MemberId(memberId).stream().map(Alarm::toDto).toList();
    }
}
