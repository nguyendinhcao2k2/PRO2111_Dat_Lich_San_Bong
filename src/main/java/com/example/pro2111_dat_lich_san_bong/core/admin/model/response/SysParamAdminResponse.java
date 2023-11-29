package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SysParamAdminResponse {
    private String id;
    private String type;
    private String code;
    private String chucNang;
    private String name;
    private String note;
    private String value;
    private Integer trangThai;
}
