package com.hana.hanalink.transaction.dto.response;

public record TransactionRes(
    String memberImg,
    String memberName,
    String memberGender,
    String amount
) {
}
