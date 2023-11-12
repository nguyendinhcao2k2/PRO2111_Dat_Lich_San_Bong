package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.persistence.Column;
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
public class SanCaAdminRespone {
    private String id;

    private String idSanBong;

    private String idCa;

    private LocalDateTime thoiGianDat;

    private LocalDate ngayDenSan;

    private Double gia;

    private Integer trangThai;

    private String userId;
}
