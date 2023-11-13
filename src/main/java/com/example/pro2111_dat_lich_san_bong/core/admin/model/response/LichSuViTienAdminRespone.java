package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichSuViTienAdminRespone {

    private String id;

    private String nguoiNhan;

    private Double soTien;

    private String loaiTien;

    private LocalDateTime thoiGian;

    private TrangThaiLoaiBienDong loaiBienDong;

    private String taiKhoanVi;

    private String idViTienCoc;

    private String trangThai;
}
