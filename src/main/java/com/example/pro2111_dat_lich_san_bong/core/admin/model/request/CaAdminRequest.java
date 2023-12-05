package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CaAdminRequest {

    private String id;

    private String tenCa;

    private Time thoiGianBatDau;

    private Time thoiGianKetThuc;

    private Double giaCa;

    private Integer trangThai;
}
