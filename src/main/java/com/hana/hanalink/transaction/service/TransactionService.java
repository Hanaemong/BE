package com.hana.hanalink.transaction.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.common.PaymentTestData;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.meetingacount.domain.MeetingAccount;
import com.hana.hanalink.meetingacount.repository.MeetingAccountRepository;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.transaction.domain.Transaction;
import com.hana.hanalink.transaction.domain.TransactionType;
import com.hana.hanalink.transaction.dto.request.TransactionReq;
import com.hana.hanalink.transaction.dto.response.PaymentCardResponse;
import com.hana.hanalink.transaction.dto.response.TransactionDetailRes;
import com.hana.hanalink.transaction.dto.response.TransactionRes;
import com.hana.hanalink.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MeetingAccountRepository meetingAccountRepository;
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;

    public TransactionDetailRes getTransHistory(Long teamId){

        MeetingAccount meetingAccount = meetingAccountRepository.findMeetingAccountByTeam_TeamId(teamId);

        if (meetingAccount == null) {
            throw new EntityNotFoundException();
        }

        Account account = accountRepository.findById(meetingAccount.getAccount().getAccountId()).orElseThrow(EntityNotFoundException::new);
        List<Transaction> transactions = transactionRepository.findByAccountTo_AccountId(account.getAccountId());

        List<TransactionRes> transactionResList = transactions.stream().map(trans -> (
                trans.toTransMember(trans.getAccountTo().getMember()))).toList();

        return TransactionDetailRes.builder()
                .balance(account.getBalance()) //잔액
                .accountNumber(meetingAccount.getMeetingAccountNumber()) // 모임통장 고유번호
                .transactionResList(transactionResList.isEmpty() ? Collections.emptyList() : transactionResList)
                .build();

    }

    public PaymentCardResponse paymentCard(Long teamId, MemberDetails member) {

        MeetingAccount meetingAccount = meetingAccountRepository.findMeetingAccountByTeam_TeamId(teamId);

        if (meetingAccount == null) {
            throw new EntityNotFoundException();
        }

        Account myAccount = accountRepository.findAccountByMember_MemberId(member.getMemberId());
        String paidStore = PaymentTestData.getRandomTransTo();
        Long paidAmount = PaymentTestData.getRandomAmount();

        Transaction transaction = Transaction.builder()
                .amount(paidAmount)
                .transFrom(member.getMemberName())
                .transTo(paidStore)
                .accountFrom(myAccount)
                .accountTo(meetingAccount.getAccount())
                .type(TransactionType.PAYMENT)
                .build();

        transactionRepository.save(transaction);
        return new PaymentCardResponse(paidStore, paidAmount);
    }

    public Long paymentDues(Long teamId, TransactionReq transactionReq, MemberDetails member) {

        MeetingAccount meetingAccount = meetingAccountRepository.findMeetingAccountByTeam_TeamId(teamId);
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Account myAccount = accountRepository.findById(transactionReq.accountId()).orElseThrow(EntityNotFoundException::new);

        Transaction transaction = Transaction.builder()
                .amount(transactionReq.amount())
                .transFrom(member.getMemberName())
                .transTo(team.getTeamName())
                .accountFrom(myAccount)
                .accountTo(meetingAccount.getAccount())
                .type(TransactionType.TRANSFER)
                .build();

        return transactionRepository.save(transaction).getTransId();
    }
}
