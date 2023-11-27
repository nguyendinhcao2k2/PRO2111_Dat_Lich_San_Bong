package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.GiaoCaThongKeAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.GiaoCaThongKeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class GiaoCaThongKeAdminServiceImpl implements GiaoCaThongKeAdminService {

    @Autowired
    private GiaoCaThongKeAdminRepository giaoCaThongKeAdminRepository;

    @Override
    public Double tongTienPhatSinhCuaCacCaTheoNgay(LocalDate thoiGianKetCa) {
        try {
            Double count = giaoCaThongKeAdminRepository.tongTienPhatSinhCuaCacCaTheoNgay(thoiGianKetCa);
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
