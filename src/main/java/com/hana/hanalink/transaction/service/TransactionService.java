package com.hana.hanalink.transaction.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.common.PaymentTestData;
import com.hana.hanalink.common.exception.EntityNotFoundException;
import com.hana.hanalink.meetingacount.domain.MeetingAccount;
import com.hana.hanalink.meetingacount.repository.MeetingAccountRepository;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.team.repository.TeamRepository;
import com.hana.hanalink.transaction.domain.Transaction;
import com.hana.hanalink.transaction.domain.TransactionType;
import com.hana.hanalink.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MeetingAccountRepository meetingAccountRepository;
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;

    public void getTransHistory(Long teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        MeetingAccount meetingAccount = meetingAccountRepository.findMeetingAccountByTeam_TeamId(teamId);
        Account account = accountRepository.findById(meetingAccount.getAccount().getAccountId()).orElseThrow(EntityNotFoundException::new);
        List<Transaction> transactions = transactionRepository.findByAccountTo_AccountId(account.getAccountId());

//        for (Transaction transaction:transactions) {
//            transaction.getAccountTo().getAccountId()
//        }


    }

    public Long paymentCard(Long teamId,Long memberId) {

        MeetingAccount meetingAccount = meetingAccountRepository.findMeetingAccountByTeam_TeamId(teamId);
        Account myAccount = accountRepository.findAccountByMember_MemberId(memberId);
        //Member member = memberRepository.findById(memberId);
        //Member member = memberRepository.findById(meetingAccount.memberId);

        Transaction transaction = Transaction.builder()
                .amount(PaymentTestData.getRandomAmount())
                .transFrom(new Member().getName())
                .transTo(PaymentTestData.getRandomTransTo())
                .accountFrom(myAccount)
                .accountTo(meetingAccount.getAccount())
                .type(TransactionType.PAYMENT)
                .build();

        return transactionRepository.save(transaction).getTransId();
    }
}
