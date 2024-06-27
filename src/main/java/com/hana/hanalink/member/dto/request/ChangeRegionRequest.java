package com.hana.hanalink.member.dto.request;

public record ChangeRegionRequest(
        Long memberId,
        Long siGunId,
        Long siGunGuId
) {}
