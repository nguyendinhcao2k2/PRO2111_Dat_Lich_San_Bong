package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HangKhachHangAdminRespone {
    private String id;

    private String tenHang;

    private Integer diemTichLuy;

    private Integer trang_thai;
}
