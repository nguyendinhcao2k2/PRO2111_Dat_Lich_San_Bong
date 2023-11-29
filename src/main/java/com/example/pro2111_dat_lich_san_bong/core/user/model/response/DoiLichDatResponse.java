package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoiLichDatResponse {
    List<LoaiSanUserResponse> loaiSan;

    List<CaDoiLichUserResponse> ca;

    List<HoaDonUserResponse> hoaDon;
}
