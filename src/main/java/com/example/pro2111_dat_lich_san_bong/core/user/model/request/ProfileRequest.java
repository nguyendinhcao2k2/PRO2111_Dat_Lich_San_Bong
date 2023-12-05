package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileRequest {

    private String id;
    private String email;
    private String image;
    private String taiKhoan;
    private String matKhau;
    private String soDienThoai;
    private String displayName;
    private String idChucVu;
    private String idHangKhachHang;
    private Integer trangThai;
}
