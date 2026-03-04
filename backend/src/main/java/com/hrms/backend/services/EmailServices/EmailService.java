package com.hrms.backend.services.EmailServices;

import com.hrms.backend.utils.FileUtility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendMail(String subject,String body, String[] recieptants,String[] ccs){
        try{
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage,true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body,true);
            mimeMessageHelper.setTo(recieptants);
            mimeMessageHelper.setCc(ccs);
//            mailSender.send(mailMessage);
        }catch (MessagingException e){
            log.error("at mail service :- "+ e.getMessage());
        }

    }
    public void sendMail(String subject,String body, String[] recieptants,String[] ccs, String attachmentFolderName,String attachementFileName) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setTo(recieptants);
            mimeMessageHelper.setCc(ccs);
            Resource file = FileUtility.Get(attachmentFolderName, attachementFileName);
            mimeMessageHelper.addAttachment(attachementFileName, file);
//            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            log.warn("at mail service :- " + e.getMessage());
        }
    }
}
