package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichOneRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private SYSParamUserService sysParamUserService;

    private DoiLichOneRequest doiLichOneRequest = null;

    @PostMapping("/create-payment")
    public String submitOrder(@RequestParam("amount") Integer tienSan,
                              @RequestParam("orderInfo") String ghiChu,
                              HttpServletRequest request) {
        int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());

        //tạo ra 1 luồng mới để tạo job kiểm trả tg hết tg thanh toán
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //thêm 1 phút sau khi tắt tab thanh toán thì hủy hóa đơn
                    Thread.sleep((miuteParam + 1) * 60 * 1000);
//                    huyLichByThatBai(HDCreateBill.getIdHoaDon());
                    //runJobHuyHDByOutTab.khaiBaoInfoJobHuyHoaDon(HDCreateBill.getIdHoaDon(), miuteParam+1);
                } catch (Exception e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/user/tien-coc/vnpay-payment";
        String vnpayUrl = vnPayService.createOrder(tienSan, ghiChu, baseUrl, miuteParam);
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

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date date = format.parse(paymentTime);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", currencyFormat.format(Integer.valueOf(totalPrice) / 100));
        model.addAttribute("paymentTime", simpleDateFormat.format(date));
        model.addAttribute("transactionId", transactionId);


        if (paymentStatus == 1) {

            return "DemoVNPay/SuccessOder";
        }
        return "DemoVNPay/FailOder";
    }

    @PostMapping("/one")
    @ResponseBody
    public ResponseEntity<?> findLichDat(@RequestBody DoiLichOneRequest doiLichOneRequest) {
        System.out.println(doiLichOneRequest.getNgayDoi());
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doiLichOneRequest));
    }


}
