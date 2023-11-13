package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseIdTemplate;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

/**
 * @author thepvph20110
 */
@Projection(types = {Ca.class, LoaiSan.class})
public interface CaUserResponse extends BaseIdTemplate {

    @Value("#{target.tenCa}")
    String getTenCa();

    @Value("#{target.giaSanCa}")
    String getGiaSanCa();

    @Value("#{target.thoiGianBatDau}")
    Time getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    Time getThoiGianKetThuc();

    @Value("#{target.countSanBong}")
    Integer getCountSanBong();

}
