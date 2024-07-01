package com.hana.hanalink.team.dto.response;

import lombok.Builder;

@Builder
public record TeamRes(Long teamId, String teamName, String category, Float score, String thumbNail, Integer memberCnt) {
}
