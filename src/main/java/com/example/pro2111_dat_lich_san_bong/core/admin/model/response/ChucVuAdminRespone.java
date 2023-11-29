package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChucVuAdminRespone {

    private String id;

    private String tenChucVu;

    private Integer trangThai;
}
