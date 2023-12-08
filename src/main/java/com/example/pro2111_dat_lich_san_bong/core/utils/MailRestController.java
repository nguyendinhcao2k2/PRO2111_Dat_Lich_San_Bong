package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongTinAccountThongKeReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.*;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/static")
public class MailRestController {

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private SendMailUtilsThongKe sendMailUtilsThongKe;

    @Autowired
    private AccountThongKeService accountThongKeService;

    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;
    @Autowired
    private HoaDonThongKeAdminService hoaDonThongKeAdminService;
    @Autowired
    private HoaDonSanCaThongKeAdminService hoaDonSanCaThongKeAdminService;
    @Autowired
    private GiaoCaThongKeAdminService giaoCaThongKeAdminService;
    @Autowired
    private ViTienCocAdminService viTienCocAdminService;

    @GetMapping("/send-mail-bookings")
    public String sendMailWithBookings(HttpServletRequest request) {

        Context context = new Context();
        context.setVariable("nguoiDat", "ĐẶNG VĂN SỸ");
        context.setVariable("sdt", "0369569225");
        //thời gian đặt sân
        context.setVariable("timeDat", "20/12/2023 20:00:00");
        context.setVariable("tongTien", "900,000");

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
        sendMailWithBookings.sendEmailBookings("thepvph20110@fpt.edu.vn", context, request);
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

    @GetMapping("/send-mail-thong-ke")
    public String sendMailWithThongKe() {
        List<ThongTinAccountThongKeReponse> accountThongKeReponses = accountThongKeService.getAllAccountByChucVu("ROLE_ADMIN");
        GiaoCaResponse giaoCa = giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
        Date date = new Date(giaoCa.getThoiGianNhanCa().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        Instant instant = Instant.ofEpochMilli(giaoCa.getThoiGianNhanCa().getTime());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        Double tongDoanhThuTheoNgay = hoaDonSanCaThongKeAdminService.tongTienTheoNgay(localDate);
        Double tongTienMatTheoNgay = hoaDonSanCaThongKeAdminService.tongTienMatTheoNgayVaHinhThucThanhToan(localDate, LoaiHinhThanhToan.TIEN_MAT.ordinal());
        Double tongTienMatTienCocTheoNgay = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNgay(LoaiHinhThanhToan.TIEN_MAT.ordinal(),localDate);
         Double tongTienChuyenKhoanTheoNgay = hoaDonSanCaThongKeAdminService.tongTienMatTheoNgayVaHinhThucThanhToan(localDate, LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());
        Double tongTienChuyenKhoanTienCocTheoNgay = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNgay(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal(),localDate);
        Double tongTienPhatSinhTrongCaTheoNgay = giaoCaThongKeAdminService.tongTienPhatSinhCuaCacCaTheoNgay(localDate);
        Integer luotSanDatOnlineTheoNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal(), localDate);
        Integer luotSanDatNhoTheoNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal(), localDate);
        Integer tongSoLuotDaTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal(), localDate);
        Integer tongSoLuotChuyenLichTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DOI_LICH.ordinal(), localDate);
        Integer tongSoLuotHuyTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.HUY_LICH.ordinal(), localDate);
        try {
            Context context = new Context();
            context.setVariable("ngayBaoCao", dateFormat.format(date));
            context.setVariable("soLichDatOnline", luotSanDatOnlineTheoNgay);
            context.setVariable("soLichDatNho", luotSanDatNhoTheoNgay);
            context.setVariable("soLuotDaTrongNgay", tongSoLuotDaTrongNgay);
            context.setVariable("soLuotLichChuyen", tongSoLuotChuyenLichTrongNgay);
            context.setVariable("soLichHuy", tongSoLuotHuyTrongNgay);
            context.setVariable("tongTienMat", currencyFormat.format(tongTienMatTheoNgay +tongTienMatTienCocTheoNgay));
            context.setVariable("tongTienChuyenKhoan", currencyFormat.format(tongTienChuyenKhoanTheoNgay +tongTienChuyenKhoanTienCocTheoNgay));
            context.setVariable("tongTienPhatSinh", currencyFormat.format(tongTienPhatSinhTrongCaTheoNgay));
            context.setVariable("tongDoanhThu", currencyFormat.format(tongDoanhThuTheoNgay + tongTienPhatSinhTrongCaTheoNgay +tongTienMatTheoNgay +tongTienMatTienCocTheoNgay + tongTienChuyenKhoanTheoNgay +tongTienChuyenKhoanTienCocTheoNgay));
            if (accountThongKeReponses != null) {
                sendMailUtilsThongKe.sendMailThongKe(accountThongKeReponses.get(0).getEmail(), context);
            } else {
                sendMailUtilsThongKe.sendMailThongKe("sydvph19885@fpt.edu.vn", context);
            }

            return "SendMail With Thong Ke Running......";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
