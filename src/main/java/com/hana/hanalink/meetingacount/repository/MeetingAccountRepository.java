package com.hana.hanalink.meetingacount.repository;

import com.hana.hanalink.meetingacount.domain.MeetingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAccountRepository extends JpaRepository<MeetingAccount,Long> {

    MeetingAccount findMeetingAccountByTeam_TeamId(Long teamId);
}
