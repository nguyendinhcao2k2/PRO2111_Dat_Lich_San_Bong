package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChucVuAdminRequest {

    private String id;

    @NotNull
    @NotBlank
    private String tenChucVu;

    @NotNull
    private Integer trangThai;
}
