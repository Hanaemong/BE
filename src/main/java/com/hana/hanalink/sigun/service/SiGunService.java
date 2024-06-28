package com.hana.hanalink.sigun.service;

import com.hana.hanalink.sigun.dto.response.SiGunResponse;
import com.hana.hanalink.sigun.repository.SiGunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiGunService {
    private final SiGunRepository siGunRepository;

    public List<SiGunResponse> getAllSiGun(){
        return siGunRepository.findAll().stream()
                .map(siGun -> new SiGunResponse(siGun.getSiGunId(), siGun.getSiGun()))
                .toList();
    }
}
