package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThoiGianDatLichAdminRespone {
    private String id;

    private LocalDateTime ngayDat;

    private String idLoaiSan;

    private String idCa;

    private String idHoaDon;

    private Double giaDatLich;

    private Integer trangThai;
}
