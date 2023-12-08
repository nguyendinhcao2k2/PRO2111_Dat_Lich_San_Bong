package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.CaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.CheckValidCheckInService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffQRCodeService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("api/v1/staff")
public class RestControllerScanQR {

    @Autowired
    private IHoaDonSanCaStaffQRCodeService hoaDonSanCaStaffQRCodeService;
    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;

    @Autowired
    private LichSuSanBongAdminService LichSuSanBongAdminService;

    @Autowired
    private CheckValidCheckInService checkValidCheckInService;

    @Autowired
    private CaStaffRepository caStaffRepository;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @GetMapping("find-time-check-in")
    public ResponseEntity<?> findTimeCheckIn() {
        try {
            SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_DUOC_PHEP_CHECK_IN);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, Integer.valueOf(param.getValue())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, 0));
        }
    }

    @PostMapping("/camera/check-qr-code")
    public ResponseEntity<?> checkQRCode(@RequestBody String qrCode) {
        try {
            HoaDonSanCa hoaDonSanCa = hoaDonSanCaStaffQRCodeService.findFirstByMaQR(qrCode);
            if (hoaDonSanCa == null) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Không tìm thấy qr code"));
            }
            // trang thai =0 la chưa check in
            // trang thai =1 la đã check in
            if (hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal()) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Đã được check-in"));
            }
            if (hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal()) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.PAYMENT_REQUIRED, "Phiếu đã được thanh toán"));
            }
            SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.getIdSanCa()).get();
            //check đến thời gian check in chưa
            Ca ca = caStaffRepository.findById(sanCa.getIdCa()).get();
            boolean check = checkValidCheckInService.checkNgayGioCheckIn(hoaDonSanCa.getNgayDenSan(), ca.getThoiGianBatDau());
            if (!check) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.LOCKED, "Chưa đến thời gian check in"));
            }
            //check đến thời gian check in chưa
            GiaoCaResponse giaoCa = giaoCaStaffService.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);
            hoaDonSanCa.setThoiGianCheckIn(Time.valueOf(LocalTime.now()));
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal());
            if (giaoCa != null) {
//            hoaDonSanCa.setThoiGianCheckIn(new Time(new Date().getTime()));
                hoaDonSanCa.setIdGiaoCa(giaoCa.getId());
            } else {
                GiaoCaResponse giaoCaKhongHoatDong = giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
                hoaDonSanCa.setIdGiaoCa(giaoCaKhongHoatDong.getId());
            }
            hoaDonSanCaStaffQRCodeService.updateHoaDonSanCaStaffQRCode(hoaDonSanCa);


            sanCa.setTrangThai(TrangThaiSanCa.DANG_DA.ordinal());

            sanCaStaffRepository.save(sanCa);

            //create Lich su san bong
            LichSuSanBong lichSuSanBong = new LichSuSanBong();
            lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal());
            lichSuSanBong.setNgayThucHien(LocalDateTime.now());
            LichSuSanBongAdminService.createOrUpdate(lichSuSanBong);

            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Check-in thành công!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
    }
}
