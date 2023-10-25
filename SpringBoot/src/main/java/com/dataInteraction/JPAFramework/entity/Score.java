package com.dataInteraction.JPAFramework.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "db_score")
@Entity
@Data
public class Score {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    Integer id;

    @JoinColumn(name = "cid")
    @OneToOne
    Subject subject;

    @Column(name = "socre")
    double score;

    @Column(name = "uid")
    Integer uid;

}
