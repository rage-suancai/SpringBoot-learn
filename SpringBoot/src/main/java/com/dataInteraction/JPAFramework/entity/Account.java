package com.dataInteraction.JPAFramework.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "db_user")
@Entity
@Data
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @JoinColumn(name = "detail_id")
    @OneToOne(fetch = FetchType.LAZY)
    AccountDetail detail;

    @JoinColumn(name = "uid")
    @OneToMany(cascade = CascadeType.REMOVE)
    List<Score> scoreList;

}
