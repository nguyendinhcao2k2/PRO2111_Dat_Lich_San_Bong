package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;


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
    @NotNull(message = "Not null")
    private String thongTin;
    @NotNull(message = "Not null")
    private String idHoaDon;
    @NotNull(message = "Not null")
    private Integer soLanDat;
    @NotNull(message = "Not null")
    private Integer soTienGiam;
    @NotNull(message = "Not null")
    private Integer trangThai;
}
