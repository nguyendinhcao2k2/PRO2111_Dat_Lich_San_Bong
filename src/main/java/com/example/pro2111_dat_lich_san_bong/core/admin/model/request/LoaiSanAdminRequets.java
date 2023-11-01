package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanAdminRequets {

    @NotNull(message = "Not null")
    private String tenLoaiSan;

    @NotNull(message = "Not null")
    private String moTa;

    @NotNull(message = "Not null")
    private Double giaSan;

    @NotNull(message = "Not null")
    private Integer trangThai;
}
