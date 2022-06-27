package com.heech.hellcoding.core.pay.bank.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pay_bank")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long id;

    @Column(name = "bank_name")
    private String name;
}
