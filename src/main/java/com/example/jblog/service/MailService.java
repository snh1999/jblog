package com.example.jblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.jblog.filter.GenericException;
import com.example.jblog.model.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${host.url}")
    private String HOST_URL;

    @Async
    public void sendMail(Email email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("example@email.com"); // sender info
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email sent!!");
        } catch (MailException e) {
            throw new GenericException("Error, could not send mail to " + email.getRecipient());
        }
    }

    public String buildBody(String tokensString) {
        String compulsory = "Thank you for signing up to jblog, Follow the link to activate your account : " +
                HOST_URL + tokensString;
        String apology = " ... The activation email might take a while to reach, So I added this method";
        return compulsory + apology;
    }
}