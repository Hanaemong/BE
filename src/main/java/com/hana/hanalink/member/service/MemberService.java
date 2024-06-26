package com.hana.hanalink.member.service;

import com.hana.hanalink.common.jwt.JwtUtil;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.dto.request.JoinRequest;
import com.hana.hanalink.member.dto.request.LoginRequest;
import com.hana.hanalink.member.dto.request.MemberMessageRequest;
import com.hana.hanalink.member.dto.request.MemberMsgCheckRequest;
import com.hana.hanalink.member.dto.response.LoginResponse;
import com.hana.hanalink.member.dto.response.MemberMessageResponse;
import com.hana.hanalink.member.dto.response.MemberMsgCheckResponse;
import com.hana.hanalink.member.exception.*;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.sigun.domain.SiGun;
import com.hana.hanalink.sigun.repository.SiGunRepository;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import com.hana.hanalink.sigungu.repository.SiGunGuRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final SiGunRepository siGunRepository;
    private final SiGunGuRepository siGunGuRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DefaultMessageService messageService;

    @Value("${coolsms.number.from}")
    private String from;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void join(JoinRequest request) {
        memberRepository.findByPhone(request.phone()).ifPresent(member -> {
            throw new PhoneExistsException("Phone number already in use");
        });

        String encodedPassword = passwordEncoder.encode(request.password());

        SiGun siGun = siGunRepository.findById(request.siGunId())
                .orElseThrow(() -> new SiGunIdNotFoundException("Invalid siGunId"));

        SiGunGu siGunGu = siGunGuRepository.findById(request.siGunGuId())
                .orElseThrow(() -> new SiGunGuIdNotFoundException("Invalid siGunGuId"));

        Member member = Member.builder()
                .name(request.name())
                .phone(request.phone())
                .gender(request.gender())
                .password(encodedPassword)
                .siGunGu(siGunGu)
                .fcmToken(request.fcmToken())
                .profile(request.profile())
                .build();

        memberRepository.save(member);
    }

    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByPhone(request.phone())
                .orElseThrow(() -> new PhoneNotFoundException());

        if(!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new PasswordIncorrectException();
        }

        String token = jwtUtil.generateAccessToken(member.getPhone());

        return new LoginResponse(token, member.getMemberId());
    }

    public MemberMessageResponse memberMessage(MemberMessageRequest request) {
        Message message = new Message();

        Random random = new Random();
        int number = random.nextInt(10000000);
        String code = String.format("%07d", number);

        message.setFrom(from);
        message.setTo(request.phone());
        message.setText("하나링크 인증번호: " + code);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        verificationCodes.put(request.phone(), code);

        return new MemberMessageResponse(code);
    }

    public MemberMsgCheckResponse memberMsgCheck(MemberMsgCheckRequest request) {
        String storedCode = verificationCodes.get(request.phone());

        if (storedCode != null && storedCode.equals(request.code())) {
            return new MemberMsgCheckResponse("인증번호 일치");
        } else {
            return new MemberMsgCheckResponse("인증번호 불일치");
        }
    }
}