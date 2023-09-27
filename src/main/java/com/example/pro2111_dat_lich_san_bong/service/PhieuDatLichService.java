package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;

import java.util.List;

public interface PhieuDatLichService {
    List<PhieuDatLich> getPhieuDatLichs();

    PhieuDatLich getOnePhieuDatLich(String id);

    Boolean createNewPhieuDatLich(PhieuDatLich phieuDatLich);

    Boolean updatePhieuDatLich(String id, PhieuDatLich phieuDatLich);

    Boolean deletePhieuDatLich(String id);

    PhieuDatLich findPhieuDatLichByMaQrCode(String qrCode);
}
