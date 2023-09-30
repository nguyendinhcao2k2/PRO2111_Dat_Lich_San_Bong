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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianReset;

    private double tongTienMatRut;

    private String idAccount;

    private TrangThaiGiaoCa trangThai;
}
