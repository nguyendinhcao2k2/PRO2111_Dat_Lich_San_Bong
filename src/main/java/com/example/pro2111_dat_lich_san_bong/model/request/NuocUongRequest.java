package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NuocUongRequest {

    @NotBlank(message = "Không được để trống!")
    private String tenNuocUong;

    @NotNull(message = "Không được để trống!")
    private Integer soLuong;

    @NotNull(message = "Không được để trống!")
    private Double donGia;

    @NotNull(message = "Không được để trống!")
    private Integer trangThai;
}
