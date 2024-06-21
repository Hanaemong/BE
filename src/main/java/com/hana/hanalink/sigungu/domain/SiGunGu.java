package com.hana.hanalink.sigungu.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.sigun.domain.SiGun;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "si_gun_gu")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SiGunGu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siGunGuId;

    @Column(name = "si_gun_nu",nullable = false)
    private String siGunGu;

    @ManyToOne
    @JoinColumn(name = "si_gun_id", nullable = false)
    private SiGun siGun;
}
