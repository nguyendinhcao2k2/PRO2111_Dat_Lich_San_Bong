package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

/**
 * @author caodinh
 */
@Projection(types = {Ca.class})
public interface CaStaffResponse {

    @Value("#{target.idCa}")
    String getIdCa();

    @Value("#{target.tenCa}")
    String getTenCa();

    @Value("#{target.giaCa}")
    Double getGiaCa();

    @Value("#{target.thoiGianBatDau}")
    Time getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    Time getThoiGianKetThuc();

}
