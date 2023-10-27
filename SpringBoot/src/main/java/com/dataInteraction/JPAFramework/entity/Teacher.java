/**package com.dataInteraction.JPAFramework.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "db_teacher")
@Entity
@Data
public class Teacher {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "sex")
    String sex;

}**/
