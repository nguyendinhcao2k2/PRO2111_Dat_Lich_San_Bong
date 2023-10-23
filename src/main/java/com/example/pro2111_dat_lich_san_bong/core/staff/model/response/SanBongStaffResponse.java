package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author caodinh
 */
@Projection(types = {SanBong.class, LoaiSan.class})
public interface SanBongStaffResponse {

    @Value("#{target.idSanBong}")
    String getIdSanBong();

    @Value("#{target.tenSanBong}")
    String getTenSanBong();

    @Value("#{target.giaSan}")
    Double getGiaSan();

    @Value("#{target.idLoaiSan}")
    String getIdLoaiSan();

    @Value("#{target.tenLoaiSan}")
    String getTenLoaiSan();

}
