package com.hana.hanalink.transaction.dto.request;

import com.hana.hanalink.transaction.domain.TransactionType;

public record TransactionReq(String amount, String transFrom , String transTo, TransactionType type) {
}
