package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class ThoiGianDatLichAdminRequets {
    private String id;

    @NotNull
    private LocalDateTime ngayDat;

    private String idLoaiSan;

    private String idCa;

    private String idHoaDon;

    @NotNull
    private Double giaDatLich;

    @NotNull
    private Integer trangThai;
}
