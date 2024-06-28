package com.hana.hanalink.account.dto.response;

public record AccountResponseDto(
        Long accountId,
        String accountName,
        String accountNumber,
        Long balance,
        String bank
) {}
