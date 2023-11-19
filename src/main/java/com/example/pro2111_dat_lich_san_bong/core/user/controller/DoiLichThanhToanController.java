package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("api/v1/user/tien-coc")
public class DoiLichThanhToanController {

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/create-payment")
    public String submitOrder(@RequestParam("amount") Integer tienSan,
                              @RequestParam("orderInfo") String ghiChu,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl +="/api/v1/user/tien-coc/vnpay-payment";
        String vnpayUrl = vnPayService.createOrder(tienSan, ghiChu, baseUrl);
        return "redirect:" + vnpayUrl;
    }
    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) throws ParseException {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String custom_param1 = request.getParameter("custom_param1");

        Locale locale = new Locale("vi","VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date date = format.parse(paymentTime);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", currencyFormat.format(Integer.valueOf(totalPrice)/100));
        model.addAttribute("paymentTime", simpleDateFormat.format(date));
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {

            return "DemoVNPay/SuccessOder";
        }
        return "DemoVNPay/FailOder";
    }
}
