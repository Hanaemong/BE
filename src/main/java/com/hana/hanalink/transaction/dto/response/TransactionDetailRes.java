package com.hana.hanalink.transaction.dto.response;

import java.util.List;

public record TransactionDetailRes(String accountNumber, long balance, List<TransactionRes> transactionResList) {

}
