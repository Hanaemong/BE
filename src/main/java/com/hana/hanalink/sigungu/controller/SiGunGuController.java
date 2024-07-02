package com.hana.hanalink.sigungu.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.sigungu.dto.response.SiGunGuResponse;
import com.hana.hanalink.sigungu.service.SiGunGuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sigun/{siGunId}")
@RequiredArgsConstructor
public class SiGunGuController {
    private final SiGunGuService siGunGuService;

    @GetMapping
    public SuccessResponse<List<SiGunGuResponse>> getBySiGunId(@PathVariable Long siGunId){
        List<SiGunGuResponse> siGunGuResponse = siGunGuService.getBySiGunId(siGunId);
        return SuccessResponse.success(siGunGuResponse);
    }
}
