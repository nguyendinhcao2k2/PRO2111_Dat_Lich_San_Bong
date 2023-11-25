package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/staff/thanh-toan")
public class ThanhToanHoaDonStaffController {
    @Autowired
    private VNPayService vnPayService;


    @GetMapping("/view-hoa-don")
    public String viewThanhToanHoaDon(Model model) {
        return "staff/danh-sach-hoa-don";
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public String getOneThanhToanHoaDon() {
        return "staff/thanh-toan-staff";
    }
    @GetMapping("/chuyen-khoan-thanh-cong")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
            String orderInfo = request.getParameter("vnp_OrderInfo");
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");
            String custom_param1 = request.getParameter("custom_param1");
            System.out.println("CHUYỂN KHOẢN THÀNH CÔNG");
            model.addAttribute("orderId", orderInfo);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("paymentTime", paymentTime);
            model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            return "DemoVNPay/SuccessOder";
        }
        return "DemoVNPay/FailOder";
    }
}
