package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DoThueNuocUongDichVuRequest {
    private String id;
    private String tenDichVu;
    private String image;
    private Integer soLuong;
    private Double donGia;
    private Integer trangThai;
}
