package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysParamAdminCreateRequest {
    private String type;
    private String code;
    private String chucNang;
    private String name;
    private String note;
    private String value;
    private Integer trangThai;
}
