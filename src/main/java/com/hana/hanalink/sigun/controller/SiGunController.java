package com.hana.hanalink.sigun.controller;

import com.hana.hanalink.sigun.dto.response.SiGunResponse;
import com.hana.hanalink.sigun.service.SiGunService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sigun")
@RequiredArgsConstructor
public class SiGunController {
    private final SiGunService siGunService;

    @GetMapping
    public List<SiGunResponse> getAllSiGun() {
        return siGunService.getAllSiGun();
    }
}
