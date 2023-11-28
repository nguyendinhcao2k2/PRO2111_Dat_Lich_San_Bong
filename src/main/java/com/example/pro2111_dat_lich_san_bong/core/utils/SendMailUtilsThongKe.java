package com.example.pro2111_dat_lich_san_bong.core.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendMailUtilsThongKe {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMailThongKe(String to, Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                try {
                    helper.setTo(to);
                    helper.setSubject("PHIẾU BÁO CÁO THỐNG KÊ SÂN BÓNG THEO NGÀY ");
                    String htmlContent = templateEngine.process("utill/bao-cao-thong-ke", context);
                    helper.setText(htmlContent, true);
                    mailSender.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
