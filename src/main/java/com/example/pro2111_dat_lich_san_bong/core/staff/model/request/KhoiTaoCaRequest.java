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
public class KhoiTaoCaRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Timestamp thoiGianNhanCa;

    private String idNhanVienTrongCa;

    private double tienBanDau;

    private String ghiChuPhatSinh = null;

    private String idAccount;

    private TrangThaiGiaoCa trangThai;
}
