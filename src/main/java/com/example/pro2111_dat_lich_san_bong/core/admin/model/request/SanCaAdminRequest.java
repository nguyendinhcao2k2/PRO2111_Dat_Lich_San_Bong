package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanCaAdminRequest {

    private String id;

    private String idSanBong;

    private String idCa;

    @NotNull
    private LocalDateTime thoiGianDat;

    @NotNull
    private LocalDate ngayDenSan;

    @NotNull
    private Double gia;

    @NotNull
    private Integer trangThai;

    private String userId;
}
