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
public class HangKhachHangRequest {

    @NotBlank(message = "Không được để trống!")
    private String tenHang;

    @NotNull(message = "Không được để trống!")
    private Integer diemTichLuy;

    @NotNull(message = "Không được để trống!")
    private Integer trang_thai;
}
