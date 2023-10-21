package com.springboot.CommonFrameworks.MailSending.controller;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Resource
    private JavaMailSender sender;

    @ResponseBody
    @GetMapping("/mail1")
    public String contextLoads1() {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("【电子科技大学教务处】关于近期学校对您的处分决定");
        message.setText("XXX同学您好 经监控和教务巡查发现 您近期存在旷课,迟到,早退,上课刷抖音行为" +
                "现已通知相关辅导员 请手写99999字书面检讨 并在2023年12月1日17点前交到辅导员办公室");
        message.setTo("javastudy111@163.com"); message.setFrom("javastudy111@163.com");
        sender.send(message); return "邮件已发送";

    }

    @ResponseBody
    @GetMapping("/mail2")
    public String contextLoads2() throws MessagingException {

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("Test"); helper.setText("lbwnb");
        helper.setTo("javastudy111@163.com"); helper.setFrom("javastudy111@163.com");
        sender.send(message); return "邮件已发送";

    }

}
