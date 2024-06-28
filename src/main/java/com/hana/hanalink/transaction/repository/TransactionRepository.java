package com.hana.hanalink.transaction.repository;

import com.hana.hanalink.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountTo.accountId = :accountId AND " +
            "YEAR(t.createdAt) = :year AND MONTH(t.createdAt) = :month")
    List<Transaction> findByAccountTo_AccountIdAndYearMonth(@Param("accountId") Long accountId,
                                                            @Param("year") int year,
                                                            @Param("month") int month);

}
