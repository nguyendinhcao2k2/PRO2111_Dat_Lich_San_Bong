package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CheckInResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.CaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.CheckValidCheckInService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.HoaDonStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffQRCodeService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class HoaDonStaffServiceImpl implements HoaDonStaffService {

    @Autowired
    private IHoaDonSanCaStaffQRCodeService hoaDonSanCaStaffQRCodeService;

    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;

    @Autowired
    private com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService LichSuSanBongAdminService;

    @Autowired
    private CheckValidCheckInService checkValidCheckInService;

    @Autowired
    private CaStaffRepository caStaffRepository;

    @Autowired
    private HoaDonStaffRepository hoaDonStaffRepository;

    @Override
    public List<CheckInResponse> listCheckIn(String param) {
        return hoaDonStaffRepository.listCheckIn(param);
    }

    @Override
    public ResponseEntity<?> checkIn(String param) {
        try {
            Optional<HoaDonSanCa> hoaDonSanCa = hoaDonSanCaStaffRepository.findById(param);
            if (!hoaDonSanCa.isPresent()) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Không tìm thấy qr code"));
            }
            // trang thai =0 la chưa check in
            // trang thai =1 la đã check in
            if (hoaDonSanCa.get().getTrangThai() == TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal()) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Đã được check-in"));
            }
            SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.get().getIdSanCa()).get();
            //check đến thời gian check in chưa
            Ca ca = caStaffRepository.findById(sanCa.getIdCa()).get();
            boolean check = checkValidCheckInService.checkNgayGioCheckIn(hoaDonSanCa.get().getNgayDenSan(), ca.getThoiGianBatDau());
            if (!check) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.LOCKED, "Bạn chưa đến thời gian check-in hoặc phiếu check-in đã quá hạn! "));
            }
            if (hoaDonSanCa.get().getTrangThai() == TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal()) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.PAYMENT_REQUIRED, "Phiếu đã được thanh toán"));
            }
            //check đến thời gian check in chưa
            GiaoCaResponse giaoCa = giaoCaStaffService.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);

            hoaDonSanCa.get().setThoiGianCheckIn(Time.valueOf(LocalTime.now()));
            hoaDonSanCa.get().setTrangThai(TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal());
            if (giaoCa != null) {
//            hoaDonSanCa.setThoiGianCheckIn(new Time(new Date().getTime()));
                hoaDonSanCa.get().setIdGiaoCa(giaoCa.getId());
            } else {
                GiaoCaResponse giaoCaKhongHoatDong = giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
                hoaDonSanCa.get().setIdGiaoCa(giaoCaKhongHoatDong.getId());
            }
            hoaDonSanCaStaffQRCodeService.updateHoaDonSanCaStaffQRCode(hoaDonSanCa.get());


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

    @Override
    public HoaDon getHoaDonById(String idHoaDon) {
        return hoaDonStaffRepository.findHoaDonById(idHoaDon);
    }

    @Override
    public HoaDon updateHoaDon(HoaDon hoaDon) {
        return hoaDonStaffRepository.saveAndFlush(hoaDon);
    }

    @Override
    public Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCa(Pageable pageable) {
        try {
            return hoaDonStaffRepository.findAllDataHoaDonAndHoaDonSanCa(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCaUser(Pageable pageable, String idAccount) {
        try {
            return hoaDonStaffRepository.findAllDataHoaDonAndHoaDonSanCaUser(pageable, idAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<LichSuHoaDonStaffReponse> searchLichSuHoaDon(Pageable pageable, String ten, String soDienThoaiNguoiDat) {
        try {
            return hoaDonStaffRepository.searchLichSuHoaDon(pageable, ten, soDienThoaiNguoiDat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<LichSuHoaDonStaffReponse> searchLichSuHoaDonUser(Pageable pageable, String idAccount, String ten, String soDienThoaiNguoiDat) {
        try {
            return hoaDonStaffRepository.searchLichSuHoaDonUser(pageable, idAccount, ten, soDienThoaiNguoiDat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
