package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.HoaDonThongKeAdminReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HoaDonThongKeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class HoaDonThongKeAdminServiceImpl implements HoaDonThongKeAdminService {

    @Autowired
    private HoaDonThongKeAdminReponsitory hoaDonThongKeAdminReponsitory;

    @Override
    public Integer soLuotDatOnlineTheoNgay(LocalDate ngayTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatOnlineTheoNgay(ngayTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatNhoTheoNgay(LocalDate ngayTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatNhoTheoNgay(ngayTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatOnlineTheoTuan(Integer yearWeek) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatOnlineTheoTuan(yearWeek);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatNhoTheoTuan(Integer yearWeek) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatNhoTheoTuan(yearWeek);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatOnlineTheoThang(LocalDate ngayTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatOnlineTheoThang(ngayTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatNhoTheoThang(LocalDate ngayTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatNhoTheoThang(ngayTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatOnlineTheoNam(Integer namTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatOnlineTheoNam(namTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer soLuotDatNhoTheoNam(Integer namTao) {
        try {
            Integer count = hoaDonThongKeAdminReponsitory.soLuotDatNhoTheoNam(namTao);
            if (count != null) {
                return count;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
