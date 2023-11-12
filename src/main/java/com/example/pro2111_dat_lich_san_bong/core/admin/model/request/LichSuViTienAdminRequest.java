package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
import jakarta.persistence.Column;
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
public class LichSuViTienAdminRequest {

    private String id;

    @NotNull
    private String nguoiNhan;

//    @NotNull
    private Double soTien;

//    @NotNull
    private String loaiTien;

//    @NotNull
    private LocalDateTime thoiGian;

    private TrangThaiLoaiBienDong loaiBienDong;

//    @NotNull
    private String taiKhoanVi;

//    @NotNull
    private String idViTienCoc;

    @NotNull
    private String trangThai;
}
