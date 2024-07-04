package com.hana.hanalink.alarm.service;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    /*오늘 기준으로 한달전 알람만 가져오기*/
    public List<AlarmRes>  getAlarmList(Long memberId) {

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<AlarmRes> alarms = alarmRepository.findAlarmsByMemberIdAndCreatedWithinOneMonth(memberId,oneMonthAgo).stream().map(Alarm::toDto).toList();

        if (alarms.isEmpty()) {
            return Collections.emptyList();
        }
        return alarms;
    }
}
