package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

/**
 * @author caodinh
 */
@Projection(types = {SanBong.class, SanCa.class})
public interface SanCaStaffResponse {

    @Value("#{target.idSanCa}")
    String getIdSanCa();

    @Value("#{target.thoiGianBatDau}")
    Time getThoiGianBatDau();
    @Value("#{target.idSanBong}")
    String getIdSanBong();

    @Value("#{target.trangThai}")
    Integer getTrangThai();


}
