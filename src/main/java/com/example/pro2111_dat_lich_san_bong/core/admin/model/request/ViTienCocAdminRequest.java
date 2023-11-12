package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
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
public class ViTienCocAdminRequest {

    protected String id;

    @NotNull
    private Timestamp thoiGianTao;

    @NotNull
    private Double soTien;

    @NotNull
    private String loaiTien;

    @NotNull
    private Integer trangThai;

    @NotNull
    private String taiKhoanVi;

    private String idHoaDon;
}
