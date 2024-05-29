package org.example.ebookstore.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.JavaMailUntil;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.service.IdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@RestController
public class IdentifyController {
    @Autowired
    private IdentifyService identifyService;

    @PostMapping("/getIdentifyCode")
    public Result getIdentifyCode(@RequestBody String emailAddress) {
        Integer code = identifyService.newCode(emailAddress);
        try {
            String stringcode=code.toString();
            while (stringcode.length()<6) {
                stringcode="0"+stringcode;
            }
            String content="[E-BookStore]验证码："+stringcode+"。该验证码10分钟内有效，请于有效期内完成验证，验证码请勿透露给他人，若非本人操作请忽略此邮件。";
            /* 创建Session会话 */
            Session session = JavaMailUntil.createSession();
            /* 创建邮件对象 */
            MimeMessage message = new MimeMessage(session);
            message.setSubject("E-BookStore验证码");
            message.setText(content);
            message.setFrom(new InternetAddress("jmoments@126.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailAddress));
            /* 发送 */
            Transport.send(message);
            return Result.success();
        }
        catch (AddressException e) {
            e.printStackTrace();
            return Result.error("111");
        } catch (MessagingException e) {
            e.printStackTrace();
            return Result.error("发送失败，请检查邮箱地址是否正确");
        }
    }

    @PostMapping("/Identify")
    public Result Identify(@RequestParam("email") String emailAddress,@RequestParam("code")String code) {
        Integer rightCode=identifyService.getCode(emailAddress);
        if(rightCode==-1){
            return Result.error("验证码已过期，请点击按钮重新发送");
        }
        else {
            if(rightCode==Integer.parseInt(code)){
                return Result.success();
            }
            else {
                return Result.error("验证码错误");
            }
        }
    }
}
