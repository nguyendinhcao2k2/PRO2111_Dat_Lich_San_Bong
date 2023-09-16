package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoaiSanRequest {

    @NotBlank(message = "Không được để trống!")
    private String tenLoaiSan;

    @NotBlank(message = "Không được để trống!")
    private String moTa;
}
