package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ThongKeAdminReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ThongKeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongKeAdminServiceImpl implements ThongKeAdminService {

    @Autowired
    private ThongKeAdminReponsitory thongKeAdminReponsitory;

    @Override
    public List<ThongKeTheoNamAdminResponse> sumTongTienByMonthAndYear(Integer year) {
        try {
            List<ThongKeTheoNamAdminResponse> sum = thongKeAdminReponsitory.sumTongTienByMonthAndYear(year);
            return sum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double thongKeTheoCaTrongNgay(Integer ngay, Integer thang, Integer nam, String timeStart, String timeEnd) {
        try {
            return thongKeAdminReponsitory.thongKeTheoCaTrongNgay(ngay, thang, nam, timeStart, timeEnd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeDoThueTheoNam(Integer year) {
        try {
            return thongKeAdminReponsitory.findThongKeDoThueTheoNam(year);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeDoThueTheoThangTrongNam(Integer year, Integer month) {
        try {
            return thongKeAdminReponsitory.findThongKeDoThueTheoThangTrongNam(year, month);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeDoThueTheoNgay(Integer year, Integer month, Integer day) {
        try {
            return thongKeAdminReponsitory.findThongKeDoThueTheoNgay(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeNuocUongTheoNam(Integer year) {
        try {
            return thongKeAdminReponsitory.findThongKeNuocUongTheoNam(year);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeNuocUongTheoThangTrongNam(Integer year, Integer month) {
        try {
            return thongKeAdminReponsitory.findThongKeNuocUongTheoThangTrongNam(year, month);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findThongKeNuocUongTheoNgay(Integer year, Integer month, Integer day) {
        try {
            return thongKeAdminReponsitory.findThongKeNuocUongTheoNgay(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
