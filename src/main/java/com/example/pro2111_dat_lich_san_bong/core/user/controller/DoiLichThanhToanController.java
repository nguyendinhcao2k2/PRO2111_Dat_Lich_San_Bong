package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichOneRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("api/v1/user/tien-coc")
public class DoiLichThanhToanController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    private DoiLichOneRequest doiLichOneRequest = new DoiLichOneRequest();

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private DoiLichUserService doiLichUserService;

    private int miuteParam = 0;

    private HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
    private SanCa sanCaUpdate = new SanCa();
    private HoaDon hoaDon = new HoaDon();
    private HoaDonSanCa hoaDonSanCa = new HoaDonSanCa();
    private SanCa sanCa = new SanCa();
    private  HoaDonSanCa hoaDonSanCaCuDaSetTrangThai = new HoaDonSanCa();

    @Autowired
    private HoaDonDoiLichUserService hoaDonDoiLichUserService;

    @PostMapping("/one")
    @ResponseBody
    public ResponseEntity<?> findLichDat(@RequestBody DoiLichOneRequest doiLichOneRequestUpdate) {
        doiLichOneRequest = doiLichOneRequestUpdate;
        System.out.println("hello:" + doiLichOneRequest.getNgayDoi());
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doiLichOneRequestUpdate));
    }

    @PostMapping("/create-payment")
    public String submitOrder(@RequestParam("amount") Integer tienSan,
                              @RequestParam("orderInfo") String ghiChu,
                              HttpServletRequest request) throws ParseException {


        miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("aaa" + doiLichOneRequest.getNgayDoi());
        LocalDate ngayDenSan = LocalDate.parse(doiLichOneRequest.getNgayDoi(), timeFormatter);

        Date date = simpleDateFormat.parse(doiLichOneRequest.getNgayDoi());

        String idCaAndLoaiSan = doiLichOneRequest.getIdCa() + "+" + doiLichOneRequest.getIdLoaiSan() + "+" + dateFormat.format(date);
        String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, doiLichOneRequest.getIdLoaiSan());


        hoaDonSanCa = hoaDonSanCaUserService.findById(doiLichOneRequest.getIdHDSC());
        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());

        sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());


        hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
        hoaDonSanCaUpdate.setTienSan(Double.valueOf(doiLichOneRequest.getTienSan()));
        hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
        hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());
        hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
        hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
        hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
        hoaDonSanCaUpdate.setCountLich(1);
        hoaDonSanCaUpdate.setTienCocThua(Double.valueOf(doiLichOneRequest.getTienCocThua()));

        hoaDon = hoaDonDoiLichUserService.findById(hoaDonSanCa.getIdHoaDon());

        //id san ca
        String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;
        hoaDonSanCaUpdate.setIdSanCa(idSanCa);

        //hoa don san ca cu
        hoaDonSanCaCuDaSetTrangThai = hoaDonSanCa;

        //san ca

        sanCaUpdate.setNgayDenSan(ngayDenSan);
        sanCaUpdate.setGia(Double.valueOf(doiLichOneRequest.getTienSan()));
        sanCaUpdate.setIdCa(doiLichOneRequest.getIdCa());
        sanCaUpdate.setId(idSanCa);
        sanCaUpdate.setTrangThai(sanCa.getTrangThai());
        sanCaUpdate.setIdSanBong(idSanBongCheck);
        sanCaUpdate.setUserId(sanCa.getUserId());
        sanCaUpdate.setThoiGianDat(sanCa.getThoiGianDat());

        //cap nhat hoa don san ca update
        hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaUpdate);
        sanCaUserService.saveSanCa(sanCaUpdate);

        // thay đổi trạng thái sân cũ
        hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaCuDaSetTrangThai);



        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/user/tien-coc/vnpay-payment";
        String vnpayUrl = vnPayService.createOrder(tienSan, ghiChu, baseUrl, miuteParam);
        return "redirect:" + vnpayUrl;
    }

//    private void saveLichDat() throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        System.out.println("aaa"+doiLichOneRequest.getNgayDoi());
//        LocalDate ngayDenSan = LocalDate.parse(doiLichOneRequest.getNgayDoi(), timeFormatter);
//
//        Date date = simpleDateFormat.parse(doiLichOneRequest.getNgayDoi());
//
//        String idCaAndLoaiSan = doiLichOneRequest.getIdCa() + "+" + doiLichOneRequest.getIdLoaiSan() + "+" + dateFormat.format(date);
//        String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, doiLichOneRequest.getIdLoaiSan());
//
//
//        HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(doiLichOneRequest.getIdHDSC());
//        SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());
//
//
//        hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
//        hoaDonSanCaUpdate.setTienSan(Double.valueOf(doiLichOneRequest.getTienSan()));
//        hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
//        hoaDonSanCaUpdate.setTrangThai(hoaDonSanCa.getTrangThai());
//        hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
//        hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
//        hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
//        hoaDonSanCaUpdate.setCountLich(1);
//        hoaDonSanCaUpdate.setTienCocThua(Double.valueOf(doiLichOneRequest.getTienCocThua()));
//
//        HoaDon hoaDon = hoaDonDoiLichUserService.findById(hoaDonSanCa.getIdHoaDon());
//        hoaDon.setTienCoc(hoaDon.getTienCoc() + doiLichOneRequest.getTienCocThieu());
//        //id san ca
//        String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;
//        hoaDonSanCaUpdate.setIdSanCa(idSanCa);
//
//
//        //san ca
//
//        sanCaUpdate.setNgayDenSan(ngayDenSan);
//        sanCaUpdate.setGia(Double.valueOf(doiLichOneRequest.getTienSan()));
//        sanCaUpdate.setIdCa(doiLichOneRequest.getIdCa());
//        sanCaUpdate.setId(idSanCa);
//        sanCaUpdate.setTrangThai(sanCa.getTrangThai());
//        sanCaUpdate.setIdSanBong(idSanBongCheck);
//        sanCaUpdate.setUserId(sanCa.getUserId());
//        sanCaUpdate.setThoiGianDat(sanCa.getThoiGianDat());
//
//        //cap nhat
//        hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaUpdate);
//        sanCaUserService.saveSanCa(sanCaUpdate);
//
//        miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());
//        //tạo ra 1 luồng mới để tạo job kiểm trả tg hết tg thanh toán
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //thêm 1 phút sau khi tắt tab thanh toán thì hủy hóa đơn
//                    Thread.sleep((miuteParam + 1) * 60 * 1000);
//                    //delete
//                    hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaUpdate.getId());
//                    sanCaUserService.deleteSanCaById(sanCaUpdate.getId());
//                } catch (Exception e) {
//                    // Handle exception
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//    }

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
        SimpleDateFormat originDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date dateTT = format.parse(paymentTime);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", currencyFormat.format(Integer.valueOf(totalPrice) / 100));
        model.addAttribute("paymentTime", originDate.format(dateTT));
        model.addAttribute("transactionId", transactionId);




        if (paymentStatus == 1) {
            //update trang thai san moi
            hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
            hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaUpdate);

            //delete sân cũ
            hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaCuDaSetTrangThai.getId());
            sanCaUserService.deleteSanCaById(sanCa.getId());


            //update hoa don
            hoaDon.setTongTien((hoaDon.getTongTien() - hoaDonSanCa.getTienSan())+ Double.valueOf(doiLichOneRequest.getTienSan()));
            hoaDon.setTienCoc(hoaDon.getTienCoc() + doiLichOneRequest.getTienCocThieu());
            hoaDonDoiLichUserService.update(hoaDon);
            return "DemoVNPay/SuccessOder";
        }
        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
        //delete sân vừa tạo
        hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaUpdate.getId());
        sanCaUserService.deleteSanCaById(sanCaUpdate.getId());
        return "DemoVNPay/FailOder";
    }




}
