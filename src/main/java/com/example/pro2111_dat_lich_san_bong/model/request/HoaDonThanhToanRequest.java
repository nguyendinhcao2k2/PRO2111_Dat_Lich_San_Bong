package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonThanhToanRequest {
    @NotNull(message = "Tổng tiền không được null")
    @Positive(message = "Tổng tiền phải là số dương")
    private Double tongTien;
    private String ghiChu;

    private String idThanhToan;
    @NotNull(message = "Thời gian tạo không được null")
    private Timestamp thoiGianTao;
    private String idHoaDon;
    @NotNull(message = "Trạng thái không được null")
    private Integer trangThai;
}
