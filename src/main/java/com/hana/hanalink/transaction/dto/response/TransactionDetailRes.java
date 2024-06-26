package com.hana.hanalink.transaction.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TransactionDetailRes(String accountNumber, long balance, List<TransactionRes> transactionResList) {

}
