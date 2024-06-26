package com.hana.hanalink.alarm.repository;

import com.hana.hanalink.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    List<Alarm> findAlarmByMember_MemberId(Long memberId);

}
