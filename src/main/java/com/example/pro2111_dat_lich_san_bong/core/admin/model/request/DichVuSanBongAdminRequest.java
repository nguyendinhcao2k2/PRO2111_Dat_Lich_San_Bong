package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DichVuSanBongAdminRequest {
    protected String id;

    @NotNull
    private Integer soLuongDoThue;

    @NotNull
    private Integer soLuongNuocUong;

    @NotNull
    private Double donGia;

    @NotNull
    private Integer trangThai;

    private String idNuocUong;

    private String idDoThue;

    private String idHoaDonSanCa;
}
