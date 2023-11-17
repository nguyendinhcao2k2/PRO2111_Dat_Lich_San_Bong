package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoDonDatLichResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ViTienUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiViTien;
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private ViTienUserService viTienUserService;

    @Autowired
    private SanCaUserService  sanCaUserService;


    @PostMapping("/create-payment")
    public String submidOrder(@RequestParam(name = "HDrequest") String HDrequest,
                              HttpServletRequest request) throws JsonProcessingException {

        HoDonDatLichResponse HDCreateBill = new ObjectMapper().readValue(HDrequest, HoDonDatLichResponse.class);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        //đường dẫn kết quả thanh toán
        baseUrl+="/api/v1/user/thanh-toan/resul-payment";
        String vnpayUrl = vnPayService.createOrder(HDCreateBill.getTienCoc().intValue(),
                HDCreateBill.getRemark(), baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/resul-payment")
    public String GetBillPayMent(HttpServletRequest request, Model model) throws ParseException {
        int paymentStatus = vnPayService.orderReturn(request);

        String idAccount = session.getUserId();

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String currCode = request.getParameter("vnp_CurrCode");


        // Định dạng mong muốn
        String outputDateFormat = "yyyy-MM-dd hh:mm:ss";

        // Tạo đối tượng SimpleDateFormat cho định dạng ban đầu
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // Tạo đối tượng SimpleDateFormat cho định dạng mong muốn
        SimpleDateFormat outputDateFormatObj = new SimpleDateFormat(outputDateFormat);

        // Parse chuỗi vào đối tượng Date
        Date date = inputDateFormat.parse(paymentTime);

        // Format lại thành chuỗi theo định dạng mong muốn
         String thoiGianGD = outputDateFormatObj.format(date);

         Double soTienGD = Double.valueOf(totalPrice) /100;

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        HoaDon hoaDon = (HoaDon) hoaDonUserService.findAllByIdAccountAndTrangThai(idAccount, TrangThaiHoaDon.MOI_TAO.ordinal()).get(0);
        if (paymentStatus == 1) {
            hoaDon.setTrangThai(TrangThaiHoaDon.DA_COC.ordinal());
            hoaDonUserService.updateHoaDon(hoaDon);
            //thanh toán thành công
            ViTienCoc viTienCoc = new ViTienCoc();
            viTienCoc.setIdHoaDon(hoaDon.getId());
            viTienCoc.setSoGiaoDich(transactionId);
            viTienCoc.setLoaiTien("VND");
            viTienCoc.setNoiDung(orderInfo);
            viTienCoc.setTrangThai(TrangThaiViTien.BINH_THUONG.ordinal());

            viTienCoc.setThoiGianTao(Timestamp.valueOf(thoiGianGD));
            viTienCoc.setSoTien(soTienGD);

            viTienUserService.saveViTen(viTienCoc);

            return "DemoVNPay/SuccessOder";
        }else {// thanh toán thất bại hoặc hết tg thanh toán
            String idHoaDon = hoaDon.getId();
            //lấy tất cả hóa đơn sân ca có idHoaDon
            List<HoaDonSanCa> list = hoaDonSanCaUserService.findAllByIdHoaDon(idHoaDon);
            for (HoaDonSanCa hoaDonSanCa: list
                 ) {
                String idSanCa = hoaDonSanCa.getIdSanCa();
                sanCaUserService.deleteSanCaById(idSanCa);
            }
            hoaDonSanCaUserService.deleteAllByIdHoaDon(idHoaDon);
            hoaDonUserService.deleteHoaDonById(idHoaDon);

            return "DemoVNPay/FailOder";
        }

//        return paymentStatus == 1 ? "DemoVNPay/SuccessOder" : "DemoVNPay/FailOder";
    }



}
