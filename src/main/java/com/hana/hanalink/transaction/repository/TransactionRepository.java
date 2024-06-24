package com.hana.hanalink.transaction.repository;

import com.hana.hanalink.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAccountTo_AccountId(Long accountId);

//    @Query("SELECT new com.hana.hanalink.transaction.dto.response.TransactionDetailRes(at.accountNumber, a.balance, new java.util.ArrayList<>(COALESCE( " +
//            "SELECT new com.hana.hanalink.transaction.dto.TransactionRes(m.img, m.name, m.gender, t.amount) " +
//            "FROM Transaction t " +
//            "JOIN t.accountTo at " +
//            "JOIN at.member m " +
//            "JOIN MeetingAccount ma ON ma.account = at " +
//            "JOIN ma.team team " +
//            "WHERE team.teamId = :teamId AND at.accountId = :accountToId, " +
//            "new java.util.ArrayList<>()))) " +
//            "FROM Account a " +
//            "JOIN a.meetingAccount ma " +
//            "JOIN ma.team team " +
//            "WHERE team.teamId = :teamId AND a.accountId = :accountToId")
//    TransactionDetailRes findAccountTransResByTeamIdAndAccountToId(@Param("teamId") Long teamId,
//                                                                   @Param("accountToId") Long accountToId);
}
