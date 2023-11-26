package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;

import java.util.List;

public interface DoiLichNhieuUserService {

    List<DoiLichNhieuUserResponse> findListLichDoi(Integer timeChoPhepDoiLich, String idNguoiDat);
}
