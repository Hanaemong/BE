package com.hana.hanalink.transaction.controller;

import com.hana.hanalink.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;

//    @GetMapping("/transaction/{teamId}")
//    public TransactionDetailRes getTransactionHistory(@PathVariable("teamId") Long teamId){
//        transactionService.getTransHistory(teamId);
//    }
//
//    @PostMapping("/transaction/{teamId}")
//    public Long createTransaction(@PathVariable("teamId") Long teamId)


//    @PostMapping("/transaction/expense/{teamId}")
//    public Long payMeetingAccount(@PathVariable("teamId") Long teamId) {
//        return transactionService.paymentCard(teamId,memberId);
//    }
}
