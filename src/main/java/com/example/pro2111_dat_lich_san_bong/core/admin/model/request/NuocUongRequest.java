package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author caodinh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NuocUongRequest {

    private String id;
    private String tenNuocUong;
    private Integer soLuong;
    private Double donGia;
    private String image;
    private String trangThai;
}
