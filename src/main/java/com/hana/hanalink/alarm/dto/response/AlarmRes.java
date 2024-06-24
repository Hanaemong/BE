package com.hana.hanalink.alarm.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlarmRes(String title, String body , LocalDateTime createdAt) {
}
