package com.hrms.backend.services.EmailServices;

import com.hrms.backend.utils.FileUtility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void shareJob(String subject, String recieptants, String body, String jdPath) throws MessagingException, MalformedURLException, FileNotFoundException {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        String htmlContent = HtmlRenderer.builder().build().render(document);
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage,true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent,true);
        mimeMessageHelper.setTo(recieptants);
        Resource file = FileUtility.Get("jds",jdPath);
        mimeMessageHelper.addAttachment(jdPath,file);
        mailSender.send(mailMessage);
    }
    public void refferJob(String subject, String[] recieptants,String[] ccs ,String body, String cvPath) throws MessagingException, MalformedURLException, FileNotFoundException {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        String htmlContent = HtmlRenderer.builder().build().render(document);
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage,true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent,true);
        mimeMessageHelper.setTo(recieptants);
        mimeMessageHelper.setCc(ccs);
        Resource file = FileUtility.Get("cvs",cvPath);
        mimeMessageHelper.addAttachment(cvPath,file);
        mailSender.send(mailMessage);
    }
}
