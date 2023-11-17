package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffQRCodeReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffQRCodeService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HoaDonSanCaStaffQRCodeServiceImpl implements IHoaDonSanCaStaffQRCodeService {

    @Autowired
    private HoaDonSanCaStaffQRCodeReponsitory hoaDonSanCaStaffQRCodeReponsitory;

    @Override
    public HoaDonSanCa findFirstByMaQR(String maQR) {
        try {
            HoaDonSanCa hoaDonSanCa = hoaDonSanCaStaffQRCodeReponsitory.findFirstByMaQR(maQR);
            if (hoaDonSanCa == null) {
                return null;
            }
            return hoaDonSanCa;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void updateHoaDonSanCaStaffQRCode(HoaDonSanCa hoaDonSanCa) {
            try {
                hoaDonSanCaStaffQRCodeReponsitory.saveAndFlush(hoaDonSanCa);
            }catch (Exception e) {
                e.printStackTrace();
            }
    }
}
