package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThongKeLoaiDichVuAdminResponse {

    private Object soLuongDichVuDaDung;
    private String tenDichVu;
    private Double donGia;
    private Double tongTienThu;
}
