package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseIdTemplate;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;


/**
 * @author thepvph20110
 */

@Projection(types = {SanCa.class, SanBong.class, Ca.class})
public interface SanCaUserResponse extends BaseIdTemplate {

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
}
