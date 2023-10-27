//package com.dataInteraction.JPAFramework.controller;
//
//import com.dataInteraction.JPAFramework.repo.AccountRepository;
//import jakarta.annotation.Resource;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class JpaDemoController {
//
//    @Resource
//    private AccountRepository accountRepository;
//
//    @ResponseBody
//    @GetMapping("/test1")
//    public void contextLoads1() {
//
//        /*Account account = new Account();
//        account.setName("小红"); account.setEmail("911@qq.com"); account.setPassword("123456");
//        System.out.println(accountRepository.save(account).getId());*/
//
//        accountRepository.findById(1).ifPresent(System.out::println);
//
//    }
//
//    @ResponseBody
//    @GetMapping("/test2")
//    public void contextLoads2() {
//
//        //accountRepository.findAccountByNameLike("%bo%").forEach(System.out::println);
//
//        //System.out.println(accountRepository.findByIdAndName(1, "bod"));
//
//        System.out.println(accountRepository.existsAccountById(1));
//
//    }
//
//    @Transactional
//    @ResponseBody
//    @GetMapping("/test3")
//    public void contextLoads3() {
//
//        //accountRepository.findById(1).ifPresent(System.out::println);
//
//        /*accountRepository.findById(1).ifPresent(account -> {
//            System.out.println(account.getName());
//            System.out.println(account.getDetail());
//        });*/
//
//        /*accountRepository.findById(1).ifPresent(account -> {
//            account.getScoreList().forEach(System.out::println);
//        });*/
//
//        accountRepository.findById(1).ifPresent(account -> {
//            account.getScoreList().forEach(score -> {
//                System.out.println("课程名称: " + score.getSubject().getName());
//                System.out.println("得分: " + score.getScore());
//                //System.out.println("任课教师: " + score.getSubject().getTeacher().getName());
//                System.out.println("任课教师: " + score.getSubject().getTeacher());
//            });
//        });
//
//    }
//
//    @Transactional
//    @ResponseBody
//    @GetMapping("/test4")
//    public void contextLoads4() {
//
//        //accountRepository.updatePasswordById(1, "98765");
//
//        accountRepository.updatePasswordByUsername("小绿", "123456");
//
//    }
//
//}
