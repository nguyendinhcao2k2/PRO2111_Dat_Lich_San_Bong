package com.example.pro2111_dat_lich_san_bong.model.request;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoCaRequest {

    private String thoiGianNhanCa;

    private String thoiGianGiaoCa;

    private String idNhanVienTrongCa;

    private String idNhanVienCaTiepTheo;

    private double tienBanDau;

    private double tongTienMat;

    private double tongTienKhac;

    private double tongTienTrongCa;

    private double tienPhatSinh;

    private String ghiChuPhatSinh;

    private float tongTienMatCaTruoc;

    private String thoiGianReset;

    private String idAccount;

    private double tongTienMatRut;

    private TrangThaiGiaoCa trangThai;
}
