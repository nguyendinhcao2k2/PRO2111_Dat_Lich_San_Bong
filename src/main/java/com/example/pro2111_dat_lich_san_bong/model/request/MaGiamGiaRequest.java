package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaGiamGiaRequest {
    @NotNull(message = "Không được để trống phần trăm mã giảm giá!")
    private Integer phanTram;

    @NotBlank(message = "Không được để trống tên mã giảm giá!")
    private String tenMaGiamGia;

    @NotNull(message ="Không được để trống ngày bắt đầu mã giảm giá")
    private Timestamp ngayBatDau;

    @NotNull(message = "Không được để trống ngày bắt đầu mã giảm giá")
    private Timestamp ngayKetThuc;

    @NotNull(message = "Không được để trống trạng thái mã giảm giá")
    private Integer trangThai;

    private String idKhachHang;

    private String idAccount;
}
