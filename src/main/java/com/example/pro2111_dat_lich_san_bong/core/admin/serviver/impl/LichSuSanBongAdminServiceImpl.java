package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.LichSuSanBongAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class LichSuSanBongAdminServiceImpl implements LichSuSanBongAdminService {

    @Autowired
    private LichSuSanBongAdminRepository lichSuSanBongAdminRepository;

    @Override
    @Transactional
    public void createOrUpdate(LichSuSanBong lichSuSanBong) {
        try {
            lichSuSanBongAdminRepository.saveAndFlush(lichSuSanBong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List list) {
        try {
            lichSuSanBongAdminRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //theo ngay
    @Override
    public Integer thongKeSoLuot(Integer trangThaiLichSu, LocalDate ngayThucHien) {
        try {
            Integer count = lichSuSanBongAdminRepository.thongKeSoLuot(trangThaiLichSu, ngayThucHien);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer thongKeSoLuotTheoTuan(Integer trangThaiLichSu, Integer yearWeek) {
        try {
            Integer count = lichSuSanBongAdminRepository.thongKeSoLuotTheoTuan(trangThaiLichSu, yearWeek);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //theo ngay

    @Override
    public Integer thongKeSoLuotTheoThang(Integer trangThaiLichSu, LocalDate ngayThucHien) {
        try {
            Integer count = lichSuSanBongAdminRepository.thongKeSoLuotTheoThang(trangThaiLichSu, ngayThucHien);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer thongKeSoLuotTheoNam(Integer trangThaiLichSu, Integer namThucHien) {
        try {
            Integer count = lichSuSanBongAdminRepository.thongKeSoLuotTheoNam(trangThaiLichSu, namThucHien);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
