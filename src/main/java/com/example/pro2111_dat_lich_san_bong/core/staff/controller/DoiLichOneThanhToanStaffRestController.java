package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichOneRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuDoiLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("api/v1/staff/tien-coc")
public class DoiLichOneThanhToanStaffRestController {

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

    @Autowired
    private LichSuDoiLichUserService lichSuDoiLichUserService;

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private ViTienUserService viTienUserService;

    @Autowired
    private HoaDonDoiLichUserService hoaDonDoiLichUserService;

    @Autowired
    private LichSuViTienUserService lichSuViTienUserService;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    @PostMapping("/one")
    @ResponseBody
    public ResponseEntity<?> findLichDat(@RequestBody DoiLichOneRequest doiLichOneRequestUpdate) {
        try {
            doiLichOneRequest = doiLichOneRequestUpdate;
            System.out.println("hello:"+doiLichOneRequestUpdate.getNgayDoi());
            System.out.println("hello:"+doiLichOneRequest.getNgayDoi());
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doiLichOneRequestUpdate));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_GATEWAY, "Error"));
        }

    }

    @PostMapping("/create-payment")
    public String submitOrder(@RequestParam("amount") Integer tienSan,
                              @RequestParam("orderInfo") String ghiChu,
                              HttpServletRequest request) throws ParseException {


        int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("aaa" + doiLichOneRequest.getNgayDoi());
        LocalDate ngayDenSan = LocalDate.parse(doiLichOneRequest.getNgayDoi(), timeFormatter);

        Date date = simpleDateFormat.parse(doiLichOneRequest.getNgayDoi());

        String idCaAndLoaiSan = doiLichOneRequest.getIdCa() + "+" + doiLichOneRequest.getIdLoaiSan() + "+" + dateFormat.format(date);
        String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, doiLichOneRequest.getIdLoaiSan());


        HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(doiLichOneRequest.getIdHDSC());
        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());

        SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());

        HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
        hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
        hoaDonSanCaUpdate.setTienSan(Double.valueOf(doiLichOneRequest.getTienSan()));
        hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
        hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());
//        hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
        hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
        hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
        hoaDonSanCaUpdate.setCountLich(1);
        hoaDonSanCaUpdate.setTienCocThua(Double.valueOf(doiLichOneRequest.getTienCocThua()));

        HoaDon hoaDon = hoaDonDoiLichUserService.findById(hoaDonSanCa.getIdHoaDon());

        //id san ca
        String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;
        hoaDonSanCaUpdate.setIdSanCa(idSanCa);


        //san ca
        SanCa sanCaUpdate = new SanCa();
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
        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());
        hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCa);

        //lich su doi lich
        LichSuDoiLich lichSuDoiLich = new LichSuDoiLich();
        lichSuDoiLich.setIdNguoiDung(commonSession.getUserId());
        lichSuDoiLich.setTrangThai(TrangThaiLichSuDoiLich.DOI_1_LICH.ordinal());
        lichSuDoiLich.setIdSanCaMoi(sanCaUpdate.getId());
        lichSuDoiLich.setIdSanCaCu(sanCa.getId());
        lichSuDoiLichUserService.save(lichSuDoiLich);

        //tạo ra 1 luồng mới để tạo job kiểm trả tg hết tg thanh toán
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //thêm 1 phút sau khi tắt tab thanh toán thì hủy hóa đơn
                    Thread.sleep((miuteParam + 1) * 60 * 1000);
                    Boolean check = huyDoiLichThatBai(hoaDonSanCaUpdate.getId());
                    if (check) {
                        //xóa sân mới
                        hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaUpdate.getId());
                        sanCaUserService.deleteSanCaById(sanCaUpdate.getId());

                        //update lại trạng thái sân cũ
                        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                        hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCa);

                        //xóa lịch sử đổi lịch
                        lichSuDoiLichUserService.deleteById(lichSuDoiLich.getId());
                    }
                } catch (Exception e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/staff/tien-coc/vnpay-payment";
        String vnpayUrl = vnPayService.createOrder(tienSan, ghiChu, baseUrl, miuteParam);
        return "redirect:" + vnpayUrl;
    }


    private Boolean huyDoiLichThatBai(String id) {
        HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(id);
        if (hoaDonSanCa != null && hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal()) {
            return true;
        }
        return false;
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
        SimpleDateFormat originDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date dateTT = format.parse(paymentTime);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", currencyFormat.format(Integer.valueOf(totalPrice) / 100));
        model.addAttribute("paymentTime", originDate.format(dateTT));
        model.addAttribute("transactionId", transactionId);
        //

        LichSuDoiLich lichSuDoiLich = lichSuDoiLichUserService.findAllByIdNguoiDungAndTrangThai(commonSession.getUserId(), TrangThaiLichSuDoiLich.DOI_1_LICH.ordinal());
        SanCa sanCaCu = sanCaUserService.findSanCaById(lichSuDoiLich.getIdSanCaCu());
        SanCa sanCaNew = sanCaUserService.findSanCaById(lichSuDoiLich.getIdSanCaMoi());
        HoaDonSanCa hoaDonSanCaCu = hoaDonSanCaUserService.findByIdSanCa(sanCaCu.getId());
        HoaDonSanCa hoaDonSanCaNew = hoaDonSanCaUserService.findByIdSanCa(sanCaNew.getId());

        if (paymentStatus == 1) {
            //update trang thai san moi
            hoaDonSanCaNew.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
            hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaNew);
//

            //delete sân cũ
            hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaCu.getId());
            sanCaUserService.deleteSanCaById(sanCaCu.getId());
//
//
//          update hoa don
            HoaDon hoaDon = hoaDonDoiLichUserService.findById(hoaDonSanCaNew.getIdHoaDon());
            hoaDon.setTongTien((hoaDon.getTongTien() - hoaDonSanCaCu.getTienSan()) + Double.valueOf(doiLichOneRequest.getTienSan()));
            hoaDon.setTienCoc(hoaDon.getTienCoc() + doiLichOneRequest.getTienCocThieu());
            hoaDonDoiLichUserService.update(hoaDon);
            // cap nhat vi tien
            ViTienCoc viTienCoc = viTienUserService.findByIdHoaDon(hoaDon.getId());
            viTienCoc.setSoTien(viTienCoc.getSoTien() + doiLichOneRequest.getTienCocThieu());
            viTienUserService.updateViTien(viTienCoc);

            //create lịch su vi tien
            LichSuViTien lichSuViTien = new LichSuViTien();
            lichSuViTien.setSoTien(doiLichOneRequest.getTienCocThieu());
            lichSuViTien.setTrangThai("0");
            lichSuViTien.setLoaiBienDong(TrangThaiLoaiBienDong.CONG_TIEN);
            lichSuViTien.setLoaiTien("VND");
            lichSuViTien.setNguoiNhan("Sân Bóng Đồng Đế");
            lichSuViTien.setThoiGian(LocalDateTime.now());
            lichSuViTien.setTaiKhoanVi("TK" + Math.floor((Math.random() * 899999) + 100000));
            lichSuViTien.setIdViTienCoc(viTienCoc.getId());
            lichSuViTienUserService.saveOrUpdate(lichSuViTien);

            //xóa lịch sử đổi lịch
            lichSuDoiLichUserService.deleteById(lichSuDoiLich.getId());

            //create Lich su san bong
            LichSuSanBong lichSuSanBong = new LichSuSanBong();
            lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DOI_LICH.ordinal());
            lichSuSanBong.setNgayThucHien(LocalDateTime.now());
            lichSuSanBongAdminService.createOrUpdate(lichSuSanBong);

            //gửi mail
            List<HoaDonSendMailResponse> list = hoaDonSanCaUserService.getLisTHDSC(hoaDon.getId());
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (list.size() == 1) {
                HoaDonSendMailResponse response = list.get(0);

                SendMailRequest sendMailRequest = new SendMailRequest();
                sendMailRequest.setTitle("Phiếu Đặt Lịch");
                sendMailRequest.setNguoiDat(response.getDisplayName());
                sendMailRequest.setNguoiNhanMail(response.getEmail());
                sendMailRequest.setQrCodeData(response.getMaQR());
                sendMailRequest.setNgayDat(formatter.format(response.getNgayTao()));
                sendMailRequest.setTimeStart(response.getThoiGianBatDau());
                sendMailRequest.setTimeEnd(response.getThoiGianKetThuc());
                sendMailRequest.setGiaTien(response.getTienSan());
                sendMailRequest.setNgayCheckIn(formatterNgayDa.format(response.getNgayDenSan()));

                sendMailUtils.sendEmail(sendMailRequest);
            } else if (list.size() > 1) {

                Context context = new Context();
                context.setVariable("nguoiDat", hoaDon.getTenNguoiDat());
                context.setVariable("sdt", hoaDon.getSoDienThoaiNguoiDat());

                //thời gian đặt sân
                context.setVariable("timeDat", hoaDon.getNgayTao());
                context.setVariable("tongTien", decimalFormat.format(hoaDon.getTongTien()));

                List<MaillListResponse> listResponses = new ArrayList<>();

                for (HoaDonSendMailResponse response : list) {
                    MaillListResponse maillListResponse = new MaillListResponse();
                    maillListResponse.setIdHoaDonSanCa(response.getId());
                    maillListResponse.setGiaSan(decimalFormat.format(response.getTienSan()));
                    maillListResponse.setNgayDa(formatterNgayDa.format(response.getNgayDenSan()));
                    maillListResponse.setCa(response.getTenCa() + ": (" + response.getThoiGianBatDau() + "-" + response.getThoiGianKetThuc() + ")");
                    listResponses.add(maillListResponse);
                }

                context.setVariable("thoiGianList", listResponses);
                sendMailWithBookings.sendEmailBookings(hoaDon.getEmail(), context, request);
            }
            //gửi mail
            return "staff/success-oder";
        }
        hoaDonSanCaCu.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
//        //delete sân vừa tạo
        hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCaNew.getId());
        sanCaUserService.deleteSanCaById(sanCaNew.getId());
        //xóa lịch sử đổi lịch
        lichSuDoiLichUserService.deleteById(lichSuDoiLich.getId());
        return "staff/fail-order-staff";
    }


}
