package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoiLichNhieuRequest {
    public String idHDSC;
    public String idCa;
    public String idLoaiSan;
    public String idSanBong;
    public String ngayDoi;
    public String tienSan;
    public Double tienCocThieu;
    public Double tienCocThua;
}
