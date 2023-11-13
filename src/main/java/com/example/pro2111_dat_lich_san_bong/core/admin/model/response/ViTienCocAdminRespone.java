package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViTienCocAdminRespone {

    protected String id;

    private Timestamp thoiGianTao;

    private Double soTien;

    private String loaiTien;

    private Integer trangThai;

    private String taiKhoanVi;

    private String idHoaDon;
}
