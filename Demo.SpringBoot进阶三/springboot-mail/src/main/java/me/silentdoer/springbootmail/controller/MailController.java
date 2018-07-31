package me.silentdoer.springbootmail.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 5:34 PM
 */
@RestController
@RequestMapping("/api/mail")
@Slf4j
public class MailController {

    @Autowired
    private JavaMailSender jms;

    @GetMapping("/sendMail")
    public String sendMail() {

        // 简单的邮件信息，没有抄送、附件等功能
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 发送者，为什么还要设置，难不成是设置昵称？，不是昵称，就是必须是账号全称而且还是必须要填写的哪怕application.properties里配置了
        // 最坑爹的是，两个地方都必须配置，否则都会出问题，why？（或者是一个邮箱账号有多个别名，这里是指定具体别名？）
        // TODO 确实，这里是设置用哪个具体别名（都对应一个邮箱账号，一箱双号都能发邮件）
        mailMessage.setFrom("13136161100@163.com");
        // 接收者
        mailMessage.setTo("1010993610@qq.com");
        mailMessage.setSubject("jj###333标题啦啦啦");
        mailMessage.setText("pp具体的内容啦啦啦");
        jms.send(mailMessage);
        return "已经发送";
    }
}
