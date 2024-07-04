package com.hana.hanalink.member.dto.response;


public record LoginResponse(String accessToken, Long memberId, String name, String siGunGu) {}
