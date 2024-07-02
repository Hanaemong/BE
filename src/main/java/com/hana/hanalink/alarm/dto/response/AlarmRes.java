package com.hana.hanalink.alarm.dto.response;

import com.hana.hanalink.alarm.domain.AlarmType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlarmRes(String title, String body,String image, LocalDateTime createdAt, Boolean isSeen, AlarmType type) {
}
