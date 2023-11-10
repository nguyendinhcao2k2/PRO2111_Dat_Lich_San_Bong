package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LuatSanResponse {

    private String id;
    private String thongTin;
    private Integer trangThai;
}
