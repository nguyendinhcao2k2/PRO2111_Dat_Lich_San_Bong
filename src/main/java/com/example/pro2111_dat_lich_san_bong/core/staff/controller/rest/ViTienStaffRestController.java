package com.example.pro2111_dat_lich_san_bong.core.staff.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.HoaDonStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IViTienStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoDonDatLichResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiViTien;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/v1/staff/vi-tien")
public class ViTienStaffRestController {

    @Autowired
    private IViTienStaffService viTienStaffService;

    @Autowired
    private HoaDonStaffService hoaDonStaffService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;


    @PostMapping("/them-vi-tien")
    public BaseResponse<Object> createViTien(@RequestBody HoDonDatLichResponse request, HttpServletRequest httpServletRequest){
        try {
            ViTienCoc viTienCoc = new ViTienCoc();
            viTienCoc.setSoTien(request.getTienCoc());
            viTienCoc.setLoaiTien("VND");
            viTienCoc.setNoiDung(request.getRemark());
            viTienCoc.setTrangThai(TrangThaiViTien.BINH_THUONG.ordinal());
            viTienCoc.setThoiGianTao(new Timestamp(System.currentTimeMillis()));
            viTienCoc.setIdHoaDon(request.getIdHoaDon());
            viTienCoc.setTypePayment(LoaiHinhThanhToan.TIEN_MAT.ordinal());
            viTienStaffService.saveViTien(viTienCoc);

            HoaDon hoaDon = hoaDonStaffService.getHoaDonById(request.getIdHoaDon());
            hoaDon.setTrangThai(TrangThaiHoaDon.DA_COC.ordinal());
            hoaDonStaffService.updateHoaDon(hoaDon);

            List<HoaDonSanCa> listHdSC = hoaDonSanCaUserService.findByIdHoaDon(hoaDon.getId());
            List<LichSuSanBong> listCreate = new ArrayList<>();
            for (HoaDonSanCa hoaDonSanCa : listHdSC) {
                LichSuSanBong lichSuSanBong = new LichSuSanBong();
                lichSuSanBong.setNgayThucHien(LocalDateTime.now());
                lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal());
                listCreate.add(lichSuSanBong);
            }
            lichSuSanBongAdminService.saveAll(listCreate);

            //gửi mail
            try {

                List<HoaDonSendMailResponse> list = hoaDonSanCaUserService.getLisTHDSC(hoaDon.getId());
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

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
                    context.setVariable("timeDat", formatter.format(hoaDon.getNgayTao()));
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
                    sendMailWithBookings.sendEmailBookings(hoaDon.getEmail(), context, httpServletRequest);
                }

            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }

            return new BaseResponse<>(HttpStatus.OK, "Đặt lịch thành công");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Đặt lịch không thành công. Vui lòng thực hiện lại");
        }

    }
}
