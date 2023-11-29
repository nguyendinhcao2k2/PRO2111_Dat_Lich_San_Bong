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
public class HangKhachHangAdminRequets {
    private String id;

    @NotNull
    private String tenHang;

    @NotNull
    private Integer diemTichLuy;

    @NotNull
    private Integer trang_thai;
}
