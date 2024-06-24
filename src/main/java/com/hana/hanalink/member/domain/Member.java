package com.hana.hanalink.member.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.sigungu.domain.SiGunGu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Column(name = "profile", nullable = false)
    private String profile;

    @OneToOne
    @JoinColumn(name = "si_gun_gu_id")
    private SiGunGu SiGunGu;
}
