package com.dataInteraction.JPAFramework.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "db_account_detail")
@Entity
@Data
public class AccountDetail {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;

    @Column(name = "real_name")
    String realName;

}
