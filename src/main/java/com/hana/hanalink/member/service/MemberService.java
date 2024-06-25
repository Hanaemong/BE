package com.hana.hanalink.member.service;

import com.hana.hanalink.common.jwt.JwtUtil;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.dto.request.JoinRequest;
import com.hana.hanalink.member.dto.request.LoginRequest;
import com.hana.hanalink.member.dto.response.LoginResponse;
import com.hana.hanalink.member.exception.PasswordNotFoundException;
import com.hana.hanalink.member.exception.PhoneNumberExistsException;
import com.hana.hanalink.member.exception.SiGunGuIdNotFoundException;
import com.hana.hanalink.member.exception.SiGunIdNotFoundException;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.sigun.domain.SiGun;
import com.hana.hanalink.sigun.repository.SiGunRepository;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.sigungu.repository.SiGunGuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final SiGunRepository siGunRepository;
    private final SiGunGuRepository siGunGuRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void join(JoinRequest request) {
        memberRepository.findByPhone(request.phone()).ifPresent(member -> {
            throw new PhoneNumberExistsException("Phone number already in use");
        });

        SiGun siGun = siGunRepository.findById(request.siGunId())
                .orElseThrow(() -> new SiGunIdNotFoundException("Invalid siGunId"));

        SiGunGu siGunGu = siGunGuRepository.findById(request.siGunGuId())
                .orElseThrow(() -> new SiGunGuIdNotFoundException("Invalid siGunGuId"));

        Member member = Member.builder()
                .name(request.name())
                .phone(request.phone())
                .gender(request.gender())
                .password(passwordEncoder.encode(request.password()))
                .siGunGu(siGunGu)
                .fcmToken(request.fcmToken())
                .profile(request.profile())
                .build();

        memberRepository.save(member);
    }

    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findAll().stream()
                .filter(m -> passwordEncoder.matches(request.password(), m.getPassword()))
                .findFirst()
                .orElseThrow(() -> new PasswordNotFoundException());

        String token = jwtUtil.generateAccessToken(member.getMemberId());

        return new LoginResponse(token, member.getMemberId());
    }
}
