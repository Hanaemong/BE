package com.hana.hanalink.member.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.account.util.AccountNumberGenerator;
import com.hana.hanalink.accountto.domain.AccountTo;
import com.hana.hanalink.accountto.repository.AccountToRepository;
import com.hana.hanalink.common.geocoding.GeocodingUtil;
import com.hana.hanalink.common.jwt.JwtUtil;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.dto.request.*;
import com.hana.hanalink.member.dto.response.*;
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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    private final GeocodingUtil geocodingUtil;
    private final AccountRepository accountRepository;
    private final AccountToRepository accountToRepository;

    @Value("${coolsms.number.from}")
    private String from;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @Transactional
    public void join(JoinRequest request) {
        memberRepository.findByPhone(request.phone()).ifPresent(member -> {
            throw new PhoneExistsException("Phone number already in use");
        });

        String encodedPassword = passwordEncoder.encode(request.password());

        SiGun siGun = siGunRepository.findById(request.siGunId())
                .orElseThrow(() -> new SiGunIdNotFoundException());

        SiGunGu siGunGu = siGunGuRepository.findById(request.siGunGuId())
                .orElseThrow(() -> new SiGunGuIdNotFoundException());

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

        createAccountForMember(member);
    }

    private void createAccountForMember(Member member){
        Account account = Account.builder()
                .accountName(generateRandomAccountName())
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .balance(500000L)
                .bank("하나은행")
                .member(member)
                .build();

        accountRepository.save(account);
        accountToRepository.save(AccountTo.builder().account(account).build());
    }

    private String generateRandomAccountName(){
        String[] accountNames = {"영하나플러스 통장", "주거래하나 통장", "하나플러스 통장"};
        Random random = new Random();
        int index = random.nextInt(accountNames.length);
        return accountNames[index];
    }

    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByPhone(request.phone())
                .orElseThrow(() -> new PhoneNotFoundException());

        if(!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new PasswordIncorrectException();
        }

        String token = jwtUtil.generateAccessToken(member.getPhone());
        member.setFcmToken(request.fcmToken());
        memberRepository.save(member);

        return new LoginResponse(token, member.getMemberId(), member.getName(), member.getSiGunGu().getSiGunGu());
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

        return new MemberMessageResponse();
    }

    public MemberMsgCheckResponse memberMsgCheck(MemberMsgCheckRequest request) {
        String storedCode = verificationCodes.get(request.phone());

        if (storedCode != null && storedCode.equals(request.code())) {
            return new MemberMsgCheckResponse("인증번호 일치");
        } else {
            return new MemberMsgCheckResponse("인증번호 불일치");
        }
    }

    // 위도, 경도를 주소로 변환 / 시군, 시군구와 일치하는지 확인
    public Mono<CheckLocationResponse> checkLocation(double latitude, double longitude, Long siGunId, Long siGunGuId){
        return geocodingUtil.getAddress(latitude, longitude)
                .map(address -> {
                    SiGun siGun = siGunRepository.findById(siGunId)
                            .orElseThrow(() -> new SiGunIdNotFoundException());
                    SiGunGu siGunGu = siGunGuRepository.findById(siGunGuId)
                            .orElseThrow(() -> new SiGunGuIdNotFoundException());

                    String siGunName = siGun.getSiGun();
                    String siGunGuName = siGunGu.getSiGunGu();
                    String region = siGunName + " " + siGunGuName;

                    boolean match = address.contains(siGunName) && address.contains(siGunGuName);

                    return new CheckLocationResponse(match, address, region);
                });
    }

    @Transactional
    public void changeRegion(ChangeRegionRequest request, String authenticatedPhone) {
        Member member = memberRepository.findByPhone(authenticatedPhone)
                .orElseThrow(() -> new MemberNotFoundException());

        SiGun siGun = siGunRepository.findById(request.siGunId())
                .orElseThrow(() -> new SiGunIdNotFoundException());

        SiGunGu siGunGu = siGunGuRepository.findById(request.siGunGuId())
                .orElseThrow(() -> new SiGunGuIdNotFoundException());

        member.setSiGunGu(siGunGu);
    }

    public MemberResponse getMemberByPhone(String phone){
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new MemberNotFoundException());

        return new MemberResponse(
                member.getName(),
                member.getPhone(),
                member.getGender(),
                member.getProfile(),
                member.getSiGunGu().getSiGun().getSiGun(),
                member.getSiGunGu().getSiGunGu()
        );
    }

    public boolean checkPhoneExists(String phone) {
        return memberRepository.findByPhone(phone).isPresent();
    }
}