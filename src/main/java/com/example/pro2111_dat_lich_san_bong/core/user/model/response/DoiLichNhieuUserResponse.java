package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoiLichNhieuUserResponse {
    private String id;
    private LocalDate ngayDenSan;
    private Double tienSan;
    private String idHoaDon;
    private String idSanCa;
    private Integer trangThai;
    private String idCa;
    private String tenCa;
    private Time thoiGianBatDau;
    private Time thoiGianKetThuc;
    private Double giaCa;
    private String idSanBong;
    private String tenSanBong;
    private String idLoaiSan;
    private String tenLoaiSan;
    private Double giaSan;
    private Integer countDoiLich;
}
