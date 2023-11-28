package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HoaDonThanhToanRequest {
    private String id;
    private Double tienCoc;
    private Double tienCocThua;
    private String tenKhachHang;
    private String soDienThoai;
    private String tenSanBong;
    private String tenLoaiSan;
    private String tenCa;
    private Time thoiGianBatDau;
    private Time thoiGianKetThuc;
    private LocalDate ngayDenSan;
    private Time thoiGianCheckIn;
    private LocalDateTime ngayDatLich;
    private Double tongTienSanCa;
    private Integer trangThai;
}
