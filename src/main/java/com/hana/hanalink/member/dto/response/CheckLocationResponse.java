package com.hana.hanalink.member.dto.response;

public record CheckLocationResponse(
        boolean match,
        String address,
        String region
) {}
