package com.hana.hanalink.alarm.repository;

import com.hana.hanalink.alarm.domain.Alarm;
import com.hana.hanalink.alarm.domain.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    @Query("SELECT a FROM Alarm a WHERE a.member.memberId = :memberId AND a.createdAt >= :oneMonthAgo ORDER BY a.createdAt DESC")
    List<Alarm> findAlarmsByMemberIdAndCreatedWithinOneMonth(@Param("memberId") Long memberId, @Param("oneMonthAgo") LocalDateTime oneMonthAgo);


    List<Alarm> findAlarmsByMember_MemberIdAndTypeAndTeam_TeamId(Long memberId, AlarmType type,Long teamId);

}
