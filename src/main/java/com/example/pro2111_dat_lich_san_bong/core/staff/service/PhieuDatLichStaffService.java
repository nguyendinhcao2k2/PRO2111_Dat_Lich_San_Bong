package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiCheckIn;
import org.springframework.http.HttpStatus;

public interface PhieuDatLichStaffService {
    PhieuDatLich findPhieuDatLichByMaQrCode(String maQrCode);

    void UpdatePhieuDatLich(PhieuDatLich phieuDatLich);

    TrangThaiCheckIn checkInSanBong(PhieuDatLich phieuDatLich);
}
