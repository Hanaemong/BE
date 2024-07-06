package com.hana.hanalink.transaction.dto.request;

public record TransactionReq(Long amount, Long accountId, String nickName) {
}
