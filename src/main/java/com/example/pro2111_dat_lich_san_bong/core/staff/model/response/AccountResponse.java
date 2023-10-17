package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {
    private String id;

    private String email;

    private String image;

    private String taiKhoan;

    private String soDienThoai;

    private String displayName;

    private Integer trangThai;

    private String idChucVu;

    private String idHangKhachHang;
    
    private String idViTien;
}
