package com.hana.hanalink.member.dto.request;

public record JoinRequest(
        String name,
        String phone,
        String gender,
        Long siGunId,
        Long siGunGuId,
        String password,
        String fcmToken,
        String profile
) {}
