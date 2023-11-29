package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.sql.Time;

@Getter
@Setter
public class CaDoiLichUserResponse {
    private String id;

    private String tenCa;

    private Time thoiGianBatDau;

    private Time thoiGianKetThuc;
    
    private Double giaCa;
}
