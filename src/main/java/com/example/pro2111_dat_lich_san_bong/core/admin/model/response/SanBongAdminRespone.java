package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanBongAdminRespone {

    private String id;

    private String tenSanBong;

    private Double giaSan;

    private String idLoaiSan;

    private Integer trangThai;

}
