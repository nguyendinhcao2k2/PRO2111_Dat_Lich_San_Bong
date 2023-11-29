package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanAdminUpdateRequest {

    private String id;
    
    private String tenLoaiSan;

    private String moTa;

    private Double giaSan;
}
