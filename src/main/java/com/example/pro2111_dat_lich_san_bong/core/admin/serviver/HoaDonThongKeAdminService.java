package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface HoaDonThongKeAdminService {
    // so luot dat san online theo ngay
    Integer soLuotDatOnlineTheoNgay(LocalDate ngayTao);

    // so luot dat san nho theo ngay
    Integer soLuotDatNhoTheoNgay(LocalDate ngayTao);

    //theo tuan
    Integer soLuotDatOnlineTheoTuan(Integer yearWeek);

    Integer soLuotDatNhoTheoTuan(Integer yearWeek);

    //theo tuan

    //theo thang

    Integer soLuotDatOnlineTheoThang(LocalDate ngayTao);

    Integer soLuotDatNhoTheoThang(LocalDate ngayTao);

    //theo thang

    //theo nam

    Integer soLuotDatOnlineTheoNam(Integer namTao);

    Integer soLuotDatNhoTheoNam(Integer namTao);

    //theo nam
}
