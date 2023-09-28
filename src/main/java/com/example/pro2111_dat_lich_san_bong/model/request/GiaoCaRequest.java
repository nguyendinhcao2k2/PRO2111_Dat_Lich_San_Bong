package com.example.pro2111_dat_lich_san_bong.model.request;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoCaRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianNhanCa;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianGiaoCa;

    private String idNhanVienTrongCa;

    private String idNhanVienCaTiepTheo;

    private double tienBanDau;

    private double tongTienMat;

    private double tongTienKhac;

    private double tongTienTrongCa;

    private double tienPhatSinh;

    private String ghiChuPhatSinh;

    private float tongTienMatCaTruoc;

    private Timestamp thoiGianReset;

    private String idAccount;

    private double tongTienMatRut;

    private TrangThaiGiaoCa trangThai;
}
