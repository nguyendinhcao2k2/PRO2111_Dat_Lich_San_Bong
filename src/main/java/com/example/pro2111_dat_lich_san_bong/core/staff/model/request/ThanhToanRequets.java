package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ThanhToanRequets {
    private String tenKhachHang;
    private String soDienThoai;
    private String tenSanBong;
    private String tenLoaiSan;
    private String tenCa;
    private Time thoiGianBatDau;
    private Time thoiGianKetThuc;
    private Double tongTienCoc;
    private Double tongTienCocThua;
    private Double tongTienDichVu;
    private Double tongTienSanBong;
    private Double tongTienPhuPhi;
}
