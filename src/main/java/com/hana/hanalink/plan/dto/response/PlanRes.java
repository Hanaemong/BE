package com.hana.hanalink.plan.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlanRes(Long planId, String planName, LocalDateTime planDate, String place, Long cost, String planImg, Boolean isSurveyed) {
}
