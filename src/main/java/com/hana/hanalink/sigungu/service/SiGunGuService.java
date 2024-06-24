package com.hana.hanalink.sigungu.service;

import com.hana.hanalink.sigungu.dto.SiGunGuResponse;
import com.hana.hanalink.sigungu.repository.SiGunGuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiGunGuService {
    private final SiGunGuRepository siGunGuRepository;

    public List<SiGunGuResponse> getBySiGunId(Long siGunId){
        return siGunGuRepository.findBySiGun_SiGunId(siGunId).stream()
                .map(siGunGu -> new SiGunGuResponse(siGunGu.getSiGunGuId(), siGunGu.getSiGunGu()))
                .toList();
    }
}
