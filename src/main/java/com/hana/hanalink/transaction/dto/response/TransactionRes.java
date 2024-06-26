package com.hana.hanalink.transaction.dto.response;

import com.hana.hanalink.transaction.domain.TransactionType;
import lombok.Builder;

@Builder
public record TransactionRes(
    String memberImg,
    String memberName,
    String memberGender,
    Long amount,
    TransactionType type
) {
}
