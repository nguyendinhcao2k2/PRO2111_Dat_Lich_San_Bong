package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

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
public class KetCaRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianKetCa;


    private String idNhanVienCaTiepTheo;

    private double tongTienMat;

    private double tongTienKhac;

    private double tongTienTrongCa;

    private double tienPhatSinh;

    private String ghiChuPhatSinh;

    private double tongTienMatCaTruoc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianReset;

    private double tongTienMatRut;

    private TrangThaiGiaoCa trangThai;
}
