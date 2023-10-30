package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

/**
 * @author caodinh
 */
@Projection(types = {HoaDon.class})
public interface HoaDonStaffResponse {

    @Value("#{target.Stt}")
    Integer getStt();

    @Value("#{target.idHoaDon}")
    String getIdHoaDon();

    @Value("#{target.tenNguoiDat}")
    String getTenNguoiDat();

    @Value("#{target.soDienThoaiNguoiDat}")
    String getSoDienThoaiNguoiDat();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.ngay}")
    LocalDateTime getNgay();

    @Value("#{target.tongTien}")
    Double getTongTien();

    @Value("#{target.tienCoc}")
    Double getTienCoc();

    @Value("#{target.maTienCoc}")
    String getMaTienCoc();

}
