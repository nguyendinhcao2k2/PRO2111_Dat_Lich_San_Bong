package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DichVuSanBongAdminRespone {
    protected String id;

    private Integer soLuongDoThue;

    private Integer soLuongNuocUong;

    private Double donGia;

    private Integer trangThai;

    private String idNuocUong;

    private String idDoThue;

    private String idHoaDonSanCa;
}
