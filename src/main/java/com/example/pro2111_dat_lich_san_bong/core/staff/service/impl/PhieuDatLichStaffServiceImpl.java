package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhieuDatLichStaffReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.PhieuDatLichStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiCheckIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class PhieuDatLichStaffServiceImpl implements PhieuDatLichStaffService {

    @Autowired
    private PhieuDatLichStaffReponsitory phieuDatLichStaffReponsitory;

    @Override
    public PhieuDatLich findPhieuDatLichByMaQrCode(String maQrCode) {
        PhieuDatLich staff = phieuDatLichStaffReponsitory.findPhieuDatLichByMaQrCode(maQrCode);
        if (staff != null) {
            return staff;
        }
        return null;
    }

    @Override
    public void UpdatePhieuDatLich(PhieuDatLich phieuDatLich) {
        phieuDatLichStaffReponsitory.saveAndFlush(phieuDatLich);
    }

    @Override
    public TrangThaiCheckIn checkInSanBong(PhieuDatLich phieuDatLich) {
        try {
            Date now = new Date();
            if (phieuDatLich != null) {
                if (phieuDatLich.getTrangThai() == 0) {
                    // check thoi gian đặt với thời gian hiện tại(DB chưa có thời gian đặt)
                    phieuDatLich.setTrangThai(1);
                    phieuDatLich.setThoiGianCheckIn(new Timestamp(now.getTime()));
                    phieuDatLichStaffReponsitory.saveAndFlush(phieuDatLich);
                    return TrangThaiCheckIn.CHECK_IN_SUCCESS;
                }
                return TrangThaiCheckIn.CHECKED_IN;
            }
            return TrangThaiCheckIn.CHECK_IN_INVALID;
        } catch (Exception e) {
            e.printStackTrace();
            return TrangThaiCheckIn.CHECK_IN_ERROR;
        }
    }
}
