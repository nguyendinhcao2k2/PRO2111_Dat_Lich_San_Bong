package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonSanCaStaffQRCodeReponsitory extends HoaDonSanCaReponsitory {

    HoaDonSanCa findFirstByMaQR(String maQR);
}
