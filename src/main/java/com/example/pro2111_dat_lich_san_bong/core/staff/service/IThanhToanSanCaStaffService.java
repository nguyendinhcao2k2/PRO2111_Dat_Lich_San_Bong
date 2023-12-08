package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;

import java.util.List;

public interface IThanhToanSanCaStaffService {
    List<HoaDonThanhToanRequest> getAllHoaDonSanCas();
    List<HoaDonThanhToanRequest> getAllHoaDonSanCaByPhone(String numberPhone);

    HoaDonThanhToanRequest getOneHoaDonThanhToan(String id);
}
