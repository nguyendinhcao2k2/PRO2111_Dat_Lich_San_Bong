package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoCaResponse {
    private String id;
    private Timestamp thoiGianNhanCa;
    private Timestamp thoiGianKetCa;
    private String idNhanVienTrongCa;
    private String idNhanVienCaTiepTheo;
    private double tienBanDau;
    private double tongTienMat;
    private double tongTienKhac;
    private double tongTienTrongCa;
    private double tienPhatSinh;
    private String ghiChuPhatSinh;
    private double tongTienMatCaTruoc;
    private Timestamp thoiGianReset;
    private double tongTienMatRut;
    private String idAccount;
    private TrangThaiGiaoCa trangThai;
}
