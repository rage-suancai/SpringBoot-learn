//package com.dataInteraction.JPAFramework.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Table(name = "db_subject")
//@Entity
//@Data
//public class Subject {
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cid")
//    @Id
//    Integer cid;
//
//    @Column(name = "name")
//    String name;
//
//    /*@Column(name = "teacher")
//    String teacher;*/
//
//    @Column(name = "time")
//    Integer time;
//
//    /*@JoinColumn(name = "tid")
//    @ManyToOne(fetch = FetchType.LAZY)
//    Teacher teacher;*/
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "teach_relation",
//            joinColumns = @JoinColumn(name = "cid"),
//            inverseJoinColumns = @JoinColumn(name = "tid"))
//    List<Teacher> teacher;
//
//}
