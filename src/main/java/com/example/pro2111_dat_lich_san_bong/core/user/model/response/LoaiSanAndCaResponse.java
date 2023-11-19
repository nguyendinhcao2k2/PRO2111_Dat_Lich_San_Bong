package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoaiSanAndCaResponse {

    List<LoaiSanUserResponse> loaiSan;

    List<CaDoiLichUserResponse> ca;
}
