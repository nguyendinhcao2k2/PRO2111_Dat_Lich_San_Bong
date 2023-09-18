package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BienDongViRequest {

    @NotNull(message = "Không được để trống người nhận")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Number nguoiNhan;

    @NotNull(message = "Không được để trống số tiền")
    private Double soTien;

    @NotBlank(message = "Không được để trống loại tiền")
    private String loaiTien;

    @NotNull(message = "Không được để trống trạng thái")
    private Integer trangThai;

    private Timestamp thoiGian;

    @NotNull(message = "Không được để trống loại biến động")
    private Integer loaiBienDong;

    @NotBlank(message = "Không được để trống tài khoản ")
    private String taiKhoanVi;
}
