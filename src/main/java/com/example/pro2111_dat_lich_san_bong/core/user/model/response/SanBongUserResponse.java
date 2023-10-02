package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.lang.annotation.Target;

/**
 * @author thepvph20110
 */

@Projection(types = {SanBong.class, LoaiSan.class})
public interface SanBongUserResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.tenSanBong}")
    String getTenSanBong();

    @Value("#{target.giaSan}")
    Double getGiaSan();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.tenLoaiSan}")
    String getTenLoaiSan();
}
