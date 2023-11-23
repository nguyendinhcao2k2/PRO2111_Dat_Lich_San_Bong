package com.example.pro2111_dat_lich_san_bong.core.schedule.model.response;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseIdTemplate;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author thepvph20110
 */
@Projection(types = {HoaDon.class, HoaDonSanCa.class, Account.class,Ca.class})
public interface HoaDonSendMailResponse extends BaseIdTemplate {

    @Value("#{target.displayName}")
    String getDisplayName();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.thoiGianBatDau}")
    String getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    String getThoiGianKetThuc();

    @Value("#{target.ngayTao}")
    LocalDateTime getNgayTao();

    @Value("#{target.ngayDenSan}")
    LocalDate getNgayDenSan();

    @Value("#{target.tienSan}")
    Double getTienSan();

    @Value("#{target.maQR}")
    String getMaQR();

    @Value("#{target.tenCa}")
    String getTenCa();
}
