package com.hana.hanalink.transaction.dto.response;

import java.time.LocalDateTime;

public record PaymentCardResponse(String paidStore, Long paidAmount, LocalDateTime paidDate) {
}
