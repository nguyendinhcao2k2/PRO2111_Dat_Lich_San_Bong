package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
@RequestMapping("/api/v1/staff")
public class DatLichStaffController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpSession session;

    @Autowired
    private IDatSanStaffService iDatSanStaffService;

    @GetMapping("/view-dat-lich")
    public String viewDatLich() {
        return "staff/nhan-vien-dat-lich";
    }

    @GetMapping("/return-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String custom_param1 = request.getParameter("custom_param1");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            ThongTinNguoiDatRequest thongTinNguoiDatRequest = (ThongTinNguoiDatRequest) session.getAttribute("thongTinNguoiDat");
            if(thongTinNguoiDatRequest != null){
                if (!iDatSanStaffService.datLich(thongTinNguoiDatRequest,request)) {
                    throw new RestApiException("Có lỗi !");
                }
            }
            session.removeAttribute("thongTinNguoiDat");
            return "staff/success-oder";
        }
        return "DemoVNPay/FailOder";
    }

}
