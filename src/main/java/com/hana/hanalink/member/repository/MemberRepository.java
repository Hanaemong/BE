package com.hana.hanalink.member.repository;

import com.hana.hanalink.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(String phone);
}
