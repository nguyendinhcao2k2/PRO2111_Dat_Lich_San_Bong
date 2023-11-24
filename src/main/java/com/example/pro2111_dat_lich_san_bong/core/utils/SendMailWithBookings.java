package com.example.pro2111_dat_lich_san_bong.core.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class SendMailWithBookings {

    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmailBookings(String to, Context context, HttpServletRequest request) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String hostAddress = getServerIPAddress(request);
        String baseUrl = request.getScheme() + "://" + hostAddress+ ":" + request.getServerPort();
        context.setVariable("hort",baseUrl);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    helper.setTo(to);
                    helper.setSubject("PHIẾU ĐẶT LỊCH SÂN BÓNG");

                    String htmlContent = templateEngine.process("utill/TemplateBookings", context);
                    helper.setText(htmlContent, true);
                    mailSender.send(message);
                } catch (MessagingException e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private String getServerIPAddress(HttpServletRequest request) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace(); // Xử lý ngoại lệ tùy thuộc vào yêu cầu của bạn
            return "Unknown";
        }
    }
}
