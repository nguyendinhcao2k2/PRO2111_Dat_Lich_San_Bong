package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.time.LocalDate;

/**
 * @author caodinh
 */

@Projection(types = {HoaDon.class, HoaDonSanCa.class, SanCa.class, SanBong.class, Ca.class})
public interface CheckInResponse {


    @Value("#{target.Stt}")
    Integer getStt();

    @Value("#{target.hoTen}")
    String getHoTen();

    @Value("#{target.soDienThoai}")
    String getSoDienThoai();

    @Value("#{target.tenSanBong}")
    String getTenSanBong();

    @Value("#{target.tenCa}")
    String getTenCa();

    @Value("#{target.thoiGianBatDau}")
    Time getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    Time getThoiGianKetThuc();

    @Value("#{target.ngayDenSan}")
    LocalDate getNgayDenSan();

    @Value("#{target.tienCoc}")
    Double getTienCoc();

    @Value("#{target.tienSan}")
    Double getTienSan();

    @Value("#{target.idHoaDon}")
    String getIdHoaDon();

    @Value("#{target.idHDSanCa}")
    String getIdHDSanCa();

    @Value("#{target.idSanCa}")
    String getIdSanCa();

    @Value("#{target.idCa}")
    String getIdCa();

    @Value("#{target.idSanBong}")
    String getIdSanBong();

}
