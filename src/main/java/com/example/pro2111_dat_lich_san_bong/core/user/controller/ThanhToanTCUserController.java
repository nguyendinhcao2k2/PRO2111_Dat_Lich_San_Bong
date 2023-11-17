package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoDonDatLichResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/api/v1/user/thanh-toan")
@SessionAttributes("HDCreateBill")
public class ThanhToanTCUserController extends BaseController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    private HoDonDatLichResponse HDCreateBill;

    @PostMapping("/create-payment")
    public String submidOrder(@RequestParam(name = "HDrequest") String HDrequest,
                              HttpServletRequest request,HttpSession session) throws JsonProcessingException {

        HoDonDatLichResponse HDCreateBill = new ObjectMapper().readValue(HDrequest, HoDonDatLichResponse.class);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        //đường dẫn kết quả thanh toán
        baseUrl+="/api/v1/user/thanh-toan/resul-payment";
        String vnpayUrl = vnPayService.createOrder(HDCreateBill.getTienCoc().intValue(),
                HDCreateBill.getRemark(), baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/resul-payment")
    public String GetBillPayMent(HttpServletRequest request, Model model,HttpSession session) {
        int paymentStatus = vnPayService.orderReturn(request);

        String idAccount = session.getId();
        HoDonDatLichResponse HDCreateBill = (HoDonDatLichResponse) session.getAttribute("HDCreateBill");
        session.removeAttribute("HDCreateBill");

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String currCode = request.getParameter("vnp_CurrCode");


        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {

            return "DemoVNPay/SuccessOder";
        }
        return "DemoVNPay/FailOder";
//        return paymentStatus == 1 ? "DemoVNPay/SuccessOder" : "DemoVNPay/FailOder";
    }

}
