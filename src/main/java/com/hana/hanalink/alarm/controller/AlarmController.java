package com.hana.hanalink.alarm.controller;


import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.service.AlarmService;
import com.hana.hanalink.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/alarm")
    public List<AlarmRes> getAlarmList(){
        return alarmService.getAlarmList(new Member().getMemberId());
    }


}
