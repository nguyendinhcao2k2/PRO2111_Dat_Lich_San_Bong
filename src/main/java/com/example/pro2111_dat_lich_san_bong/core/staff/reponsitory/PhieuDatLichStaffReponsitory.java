package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.repository.PhieuDatLichRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDatLichStaffReponsitory extends PhieuDatLichRepository {
    PhieuDatLich findPhieuDatLichByMaQrCode(String maQrCode);
}
