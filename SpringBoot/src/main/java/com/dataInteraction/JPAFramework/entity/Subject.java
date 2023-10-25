package com.dataInteraction.JPAFramework.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "db_subject")
@Entity
@Data
public class Subject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    @Id
    Integer cid;

    @Column(name = "name")
    String name;

    @Column(name = "teacher")
    String teacher;

    @Column(name = "time")
    Integer time;

}
