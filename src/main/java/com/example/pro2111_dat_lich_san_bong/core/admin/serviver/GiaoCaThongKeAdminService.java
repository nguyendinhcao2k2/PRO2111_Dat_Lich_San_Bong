package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface GiaoCaThongKeAdminService {
    Double tongTienPhatSinhCuaCacCaTheoNgay(LocalDate thoiGianKetCa);
}
