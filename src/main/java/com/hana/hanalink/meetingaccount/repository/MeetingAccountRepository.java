package com.hana.hanalink.meetingaccount.repository;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.meetingaccount.domain.MeetingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAccountRepository extends JpaRepository<MeetingAccount,Long> {

    MeetingAccount findMeetingAccountByAccount(Account account);

    boolean existsByAccount(Account account);

}
