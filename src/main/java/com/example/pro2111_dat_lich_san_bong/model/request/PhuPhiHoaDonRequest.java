package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
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
public class PhuPhiHoaDonRequest {
    @NotNull(message = "Thời gian tạo không được null")
    private Timestamp thoiGianTao;
    @NotNull(message = "Trạng thái không được null")
    private Integer trangThai;
}
