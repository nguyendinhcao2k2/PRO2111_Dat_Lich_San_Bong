package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffQRCodeService;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("api/v1/staff")
public class RestControllerScanQR {

    @Autowired
    private IHoaDonSanCaStaffQRCodeService hoaDonSanCaStaffQRCodeService;
    @Autowired
    private SanCaStaffRepository  sanCaStaffRepository;

    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;

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
            GiaoCaResponse giaoCa = giaoCaStaffService.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);

            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal());
            if(giaoCa != null) {
//            hoaDonSanCa.setThoiGianCheckIn(new Time(new Date().getTime()));
                hoaDonSanCa.setIdGiaoCa(giaoCa.getId());
            }else{
                GiaoCaResponse giaoCaKhongHoatDong = giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
                hoaDonSanCa.setIdGiaoCa(giaoCaKhongHoatDong.getId());
            }
            hoaDonSanCaStaffQRCodeService.updateHoaDonSanCaStaffQRCode(hoaDonSanCa);


            SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.getIdSanCa()).get();
            sanCa.setTrangThai(TrangThaiSanCa.DANG_DA.ordinal());

            sanCaStaffRepository.save(sanCa);

            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Check-in thành công!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
    }
}
