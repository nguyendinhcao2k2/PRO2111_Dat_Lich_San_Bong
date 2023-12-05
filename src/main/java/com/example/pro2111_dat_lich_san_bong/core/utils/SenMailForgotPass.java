package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SenMailForgotPass {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMailForGotPass(String text, String to, String sub) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setText(text);
                message.setTo(to);
                message.setSubject(sub);
                mailSender.send(message);
            }
        });
        thread.start();


    }
}
