package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface HoaDonSanCaThongKeAdminService {

    //theo ngay
    Double tongTienTheoNgay(LocalDate ngayThanhToan);

    Double tongTienMatTheoNgayVaHinhThucThanhToan(LocalDate ngayThanhToan, Integer loaiHinhThanhToan);
    //theo ngay

    //theo tuan
    Double tongTienTheoTuan(Integer yearWeek);

    Double tongTienMatTheoTuanVaHinhThucThanhToan(Integer yearWeek, Integer loaiHinhThanhToan);
    //theo tuan

    //theo thang
    Double tongTienTheoThang(LocalDate ngayThanhToan);

    Double tongTienMatTheoThangVaHinhThucThanhToan(Integer thangThanhToan,Integer namThanhToan, Integer loaiHinhThanhToan);
    //theo thang

    //theo nam
    Double tongTienTheoNam(Integer namThanhToan);

    Double tongTienMatTheoNamVaHinhThucThanhToan(Integer namThanhToan, Integer loaiHinhThanhToan);
    //theo nam
}
