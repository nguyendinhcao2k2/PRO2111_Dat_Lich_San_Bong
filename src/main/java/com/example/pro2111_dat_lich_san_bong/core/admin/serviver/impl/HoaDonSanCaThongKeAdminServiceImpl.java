package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.HoaDonSanCaThongKeAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HoaDonSanCaThongKeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class HoaDonSanCaThongKeAdminServiceImpl implements HoaDonSanCaThongKeAdminService {

    @Autowired
    private HoaDonSanCaThongKeAdminRepository hoaDonSanCaThongKeAdminRepository;

    @Override
    public Double tongTienTheoNgay(LocalDate ngayThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienTheoNgay(ngayThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienMatTheoNgayVaHinhThucThanhToan(LocalDate ngayThanhToan, Integer loaiHinhThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienMatTheoNgayVaHinhThucThanhToan(ngayThanhToan, loaiHinhThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienTheoTuan(Integer yearWeek) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienTheoTuan(yearWeek);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienMatTheoTuanVaHinhThucThanhToan(Integer yearWeek, Integer loaiHinhThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienMatTheoTuanVaHinhThucThanhToan(yearWeek, loaiHinhThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienTheoThang(LocalDate ngayThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienTheoThang(ngayThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienMatTheoThangVaHinhThucThanhToan(Integer thangThanhToan, Integer namThanhToan, Integer loaiHinhThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienMatTheoThangVaHinhThucThanhToan(thangThanhToan, namThanhToan, loaiHinhThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienTheoNam(Integer namThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienTheoNam(namThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienMatTheoNamVaHinhThucThanhToan(Integer namThanhToan, Integer loaiHinhThanhToan) {
        try {
            Double count = hoaDonSanCaThongKeAdminRepository.tongTienMatTheoNamVaHinhThucThanhToan(namThanhToan, loaiHinhThanhToan);
            if (count != null) {
                return count;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
