package com.hana.hanalink.sigungu.repository;

import com.hana.hanalink.sigungu.domain.SiGunGu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiGunGuRepository extends JpaRepository<SiGunGu, Long> {
    List<SiGunGu> findBySiGun_SiGunId(Long siGunId);
}
