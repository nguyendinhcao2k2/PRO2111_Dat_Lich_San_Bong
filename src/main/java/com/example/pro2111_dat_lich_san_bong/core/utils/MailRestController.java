package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/static")
public class MailRestController {

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @GetMapping("/send-mail-bookings")
    public String sendMailWithBookings( HttpServletRequest request) {

        Context context = new Context();
        context.setVariable("nguoiDat", "ĐẶNG VĂN SỸ");
        context.setVariable("sdt", "0369569225");
        //thời gian đặt sân
        context.setVariable("timeDat", "20/12/2023 20:00:00");
        context.setVariable("tongTien","900,000");

        List<MaillListResponse> listResponses = new ArrayList<>();
        MaillListResponse maillListResponse = new MaillListResponse();
        maillListResponse.setIdHoaDonSanCa("aa0d413e-b1c9-4173-b969-ac85f08685e7");
        maillListResponse.setGiaSan("500,000");
        maillListResponse.setNgayDa("20/02/2023");
        maillListResponse.setCa("Ca 1: (9:09:00 - 10:09:00)");
        listResponses.add(maillListResponse);

        MaillListResponse maillListResponse2 = new MaillListResponse();
        maillListResponse2.setIdHoaDonSanCa("ffffff555");
        maillListResponse2.setGiaSan("400,000");
        maillListResponse2.setNgayDa("21/02/2023");
        maillListResponse2.setCa("Ca 2: (10:00:00 - 10:30:00)");
        listResponses.add(maillListResponse2);

        context.setVariable("thoiGianList", listResponses);
        sendMailWithBookings.sendEmailBookings("thepvph20110@fpt.edu.vn", context,request);
        return "Send mail run....";
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
