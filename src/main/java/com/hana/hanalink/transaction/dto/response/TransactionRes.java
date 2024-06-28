package com.hana.hanalink.transaction.dto.response;

import com.hana.hanalink.transaction.domain.TransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionRes(
    String memberProfile,
    String memberName,
    String memberGender,
    Long amount,
    TransactionType type,

    LocalDateTime paidDate
) {
}
