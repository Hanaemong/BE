package com.hana.hanalink.transaction.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "transTo", nullable = false)
    private String transTo;

    @Column(name = "transFrom", nullable = false)
    private String transFrom;

    @Column(name = "type", nullable = false)
    private String type;
}
