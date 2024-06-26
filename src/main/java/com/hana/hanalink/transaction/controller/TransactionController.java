package com.hana.hanalink.transaction.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.transaction.dto.request.TransactionReq;
import com.hana.hanalink.transaction.dto.response.PaymentCardResponse;
import com.hana.hanalink.transaction.dto.response.TransactionDetailRes;
import com.hana.hanalink.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;

    /*거래 내역 가져오기*/
    @GetMapping("/transaction/{teamId}")
    public SuccessResponse<TransactionDetailRes> getTransactionHistory(@PathVariable("teamId") Long teamId){
        return SuccessResponse.success(transactionService.getTransHistory(teamId));
    }

    /*회비 납부하기*/
    @PostMapping("/transaction/{teamId}")
    public SuccessResponse<Long> createTransaction(@PathVariable("teamId") Long teamId, @RequestBody TransactionReq transactionReq,@AuthenticationPrincipal MemberDetails member){
        return SuccessResponse.success(transactionService.paymentDues(teamId,transactionReq,member));
    }


    /*지출하기*/
    @PostMapping("/transaction/expense/{teamId}")
    public SuccessResponse<PaymentCardResponse> payMeetingAccount(@PathVariable("teamId") Long teamId, @AuthenticationPrincipal MemberDetails member) {
        return SuccessResponse.success(transactionService.paymentCard(teamId, member));
    }
}
