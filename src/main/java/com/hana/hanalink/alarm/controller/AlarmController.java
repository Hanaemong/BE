package com.hana.hanalink.alarm.controller;


import com.hana.hanalink.alarm.dto.response.AlarmRes;
import com.hana.hanalink.alarm.service.AlarmService;
import com.hana.hanalink.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<AlarmRes> getAlarmList(@AuthenticationPrincipal MemberDetails member){
        return alarmService.getAlarmList(member.getMemberId());
    }

}
