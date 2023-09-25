package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThanhToanRequest {
    @NotBlank(message = "Loại hình thanh toán không được trống")
    private String loaiHinhThanhToan;
    @NotNull(message = "Trạng thái không được null")
    private Integer trangThai;
}
