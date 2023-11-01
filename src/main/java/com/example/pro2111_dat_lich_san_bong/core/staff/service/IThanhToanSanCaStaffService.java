package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;

import java.util.List;

public interface IThanhToanSanCaStaffService {
    List<HoaDonThanhToanRequest> getAllHoaDonSanCas(int trangThai);

    HoaDonThanhToanRequest getOneHoaDonThanhToan(String id);
}
