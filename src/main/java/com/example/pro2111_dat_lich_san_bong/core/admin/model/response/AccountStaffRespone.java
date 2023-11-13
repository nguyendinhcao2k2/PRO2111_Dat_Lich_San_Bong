package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStaffRespone {

    private String id;

    private String email;

    private String taiKhoan;

    private String soDienThoai;

    private String matKhau;

    private String displayName;

    private Integer trangThai;

    private String idChucVu;
}
