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
}
