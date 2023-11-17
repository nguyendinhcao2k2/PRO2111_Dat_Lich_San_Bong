package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;

public interface IHoaDonSanCaStaffQRCodeService {
    HoaDonSanCa findFirstByMaQR(String maQR);

    void updateHoaDonSanCaStaffQRCode(HoaDonSanCa hoaDonSanCa);
}
