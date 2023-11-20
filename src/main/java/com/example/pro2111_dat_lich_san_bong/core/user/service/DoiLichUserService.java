package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;

import java.time.LocalDate;

public interface DoiLichUserService {

    DoiLichOneUserResponse findFirstByIdSanCa(String idSanCa);

    DoiLichOneUserResponse findFirstByIdSanCaAndTrangThai(Integer trangThaiHdsc, String idSanCa);

    boolean checkNgayDenSan(LocalDate localDate);

    String getIdSanBongEmpty(String textCheck, String idLoaiSan);
}
