package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuatSanRequest {

    private String id;
    @Column(columnDefinition = "NVARCHAR(255")
    private String thongTin;
}
