package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuanLyGiaoCaResponse {
    private String id;
    private String displayNameNhanVienTrongCa;
    private String displayNameNhanVienCaTiepTheo;
    private Timestamp thoiGianNhanCa;
    private Timestamp thoiGianKetCa;
    private Timestamp thoiGianReset;
    private Double tongTienTrongCa;
    private Double tienBanDau;
    private Double tongTienMatRut;
    private Double tienPhatSinh;
    private String ghiChuPhatSinh;
    private Double tongTienMat;
    private Double tongTienKhac;
}
