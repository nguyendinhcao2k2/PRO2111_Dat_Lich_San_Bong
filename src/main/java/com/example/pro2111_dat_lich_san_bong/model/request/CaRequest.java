package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CaRequest {

    @NotBlank(message = "Không được để trống!")
    private String tenCa;

    @NotNull(message = "Không được để trống!")
    private Time thoiGianBatDau;

    @NotNull(message = "Không được để trống!")
    private Time thoiGianKetThuc;

    @NotNull(message = "Không được để trống!")
    private Double giaCa;

    @NotNull(message = "Không được để trống!")
    private Integer trangThai;
}
