package com.hana.hanalink.member.dto.response;

public record MemberResponse(
    String name,
    String phone,
    String gender,
    String profile,
    String siGun,
    String siGunGu
) {}
