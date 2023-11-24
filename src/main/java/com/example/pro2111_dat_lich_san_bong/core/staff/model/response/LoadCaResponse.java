package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author caodinh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadCaResponse {

    private String idResponse;

    private String idCa;

    private String tenCa;

    private String thoiGianBatDau;

    private String thoiGianKetthuc;

    private String date;

    private String loaiSan;

    private double gia;

    private Integer trangThai;

    private String idHoaDonSanCa;

}
