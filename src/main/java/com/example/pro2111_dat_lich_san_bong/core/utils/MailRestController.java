package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/static")
public class MailRestController {

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @PostMapping("/send-mail-bookings")
    public String sendMailWithBookings() {
        Context context = new Context();
        context.setVariable("nguoiDat", "ĐẶNG VĂN SỸ");
        context.setVariable("sdt", "0369569225");
        context.setVariable("timeDat", new Date());
        List<String> timeBookings = new ArrayList<>();
        timeBookings.add("07:00:00 - 09:00:00");
        timeBookings.add("07:00:00 - 09:00:00");
        timeBookings.add("07:00:00 - 09:00:00");
        timeBookings.add("07:00:00 - 09:00:00");
        timeBookings.add("07:00:00 - 09:00:00");
        context.setVariable("thoiGian", timeBookings);
        sendMailWithBookings.sendEmailBookings("sydvph19885@fpt.edu.vn", context);
        return "Send mail run....";
    }
}
