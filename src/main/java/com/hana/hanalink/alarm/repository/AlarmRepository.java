package com.hana.hanalink.alarm.repository;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.domain.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    List<Alarm> findAlarmByMember_MemberId(Long memberId);

    Alarm findAlarmByMember_MemberIdAndTypeAndTeam_TeamId(Long memberId, AlarmType type,Long teamId);

}
