package com.yabushan.email.service.impl;


import com.yabushan.email.service.mailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class mailServiceImpl implements mailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JavaMailSender javaMailSender;

    private String from="jing1016Vip@163.com";
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMessage=new SimpleMailMessage();
        simpleMessage.setFrom(from);
        simpleMessage.setTo(to);
        simpleMessage.setSubject(subject);
        simpleMessage.setText(content);

        try{
            javaMailSender.send(simpleMessage);
            logger.info("简单邮件已发送");
        }catch (Exception e){
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

    public void sendHtml(String to,String subject,String content) {
        MimeMessage message=javaMailSender.createMimeMessage();
       try {
           MimeMessageHelper helper=new MimeMessageHelper(message,true);
           helper.setFrom(from);
           helper.setTo(to);
           helper.setSubject(subject);
           helper.setText(content,true );

           javaMailSender.send(message);
           logger.info("html邮件发送成功");
       }catch (Exception e){
           logger.error("发送html邮件时发生异常！", e);
       }

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message=javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file=new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }
}
