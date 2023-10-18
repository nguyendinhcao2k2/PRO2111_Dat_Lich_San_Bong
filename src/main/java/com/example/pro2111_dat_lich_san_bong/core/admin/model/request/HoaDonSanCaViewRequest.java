package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonSanCaViewRequest {
    private String id;
    private String sanCa;
    private String hoaDon;
    private String tenSanBong;
    private String tenCa;
    private LocalDate ngayDenSan;
    private Time thoiGianCheckIn;
    private String tenKhachHang;
    private String soDienThoai;
    private String maQR;
    private Double tongTien;
    private Integer trangThai;

}
