package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
public class LoaiSanUserResponse {
    private String id;

    private String tenLoaiSan;

    private String moTa;
    
    private Double giaSan;
}
