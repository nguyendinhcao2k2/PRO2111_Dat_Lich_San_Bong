package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseIdTemplate;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.sql.Timestamp;


/**
 * @author thepvph20110
 */

@Projection(types = {SanCa.class, SanBong.class, Ca.class, HoaDonSanCa.class, HoaDon.class})
public interface SanCaStaffEventResponse extends BaseIdTemplate {

    @Value("#{target.idSanBong}")
    String getIdSanBong();

    @Value("#{target.gia}")
    String getGia();

    @Value("#{target.trangThai}")
    String getTrangThai();

    @Value("#{target.tenCa}")
    String getTenCa();

    @Value("#{target.thoiGianBatDau}")
    Time getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    Time getThoiGianKetThuc();

    @Value("#{target.thoiGianDat}")
    Timestamp getThoiGianDat();

    @Value("#{target.soLanDoiLich}")
    Integer getSoLanDoiLich();

    @Value("#{target.tenNguoiDat}")
    String getTenNguoiDat();
}
