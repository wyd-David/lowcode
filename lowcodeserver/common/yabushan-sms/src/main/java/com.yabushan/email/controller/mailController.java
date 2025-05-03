package com.yabushan.email.controller;

import com.yabushan.email.service.mailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 邮件发送Controller
 *
 * @author yabushan
 * @date 2021-08-13
 */
@RequestMapping("/email")
@Controller
public class mailController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private mailService mailService;
    //常规的测试能否正常发送邮件
    @RequestMapping("/send")
    public String sendSimpleMail(String toEmail,String subject,String content){
        mailService.sendSimpleMail("464816964@qq.com","make test now ","你好，我正在测试验证simpl登录");
        logger.info("测试simple完毕");
        return "message,完成";

    }

    //测试发送邮件，邮件格式是html格式
    @GetMapping("/sendHtml")
    public String sendHtmlMail(String toEmail,String subject,String content)throws Exception{
        String content1="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtml("464816964@qq.com","make test now ",content1);
        logger.info("测试完毕");
        return "message,html完成";

    }

    //测试发送邮件带附件的
    @GetMapping("/sendAttachmentsMail")
    public String sendAttachmentsMail(String toEmail,String subject,String content,String substring){
        String path = mailController.class.getClassLoader().getResource("aa.txt").getPath();
        System.out.println(path);
        String substring1 = path.substring(1, path.length());
        substring1 = substring1.replace("/", "\\");
        System.out.println(substring1);
        mailService.sendAttachmentsMail("464816964@qq.com", "主题：带附件的邮件", "有附件，请查收！", substring1);
        logger.info("测试完毕");
        return "html带附件测试完成";
    }
}
