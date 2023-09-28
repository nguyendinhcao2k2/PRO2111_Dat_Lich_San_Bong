package com.example.pro2111_dat_lich_san_bong.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonPhieuDatLichRequest {
    private String tenKhachHang;
    private String tenSanBong;
    private String tenCa;
    private Time gioBatDau;
    private Time gioKetThuc;
    private Double tongTienSanCa;
    private Timestamp thoiGianCheckIn;
    private String dichVu;
    private Integer trangThai;

}
