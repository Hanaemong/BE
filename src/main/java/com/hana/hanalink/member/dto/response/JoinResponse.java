package com.hana.hanalink.member.dto.response;

public record JoinResponse(
        String accessToken,
        Long memberId
) {}
