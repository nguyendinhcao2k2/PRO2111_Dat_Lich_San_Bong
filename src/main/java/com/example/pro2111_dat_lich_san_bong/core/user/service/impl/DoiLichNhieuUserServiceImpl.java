package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.DoiLichNhieuStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.DoiLichNhieuUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.DoiLichNhieuUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoiLichNhieuUserServiceImpl implements DoiLichNhieuUserService {

    @Autowired
    private DoiLichNhieuUserReponsitory doiLichNhieuUserReponsitory;

    @Override
    public List<DoiLichNhieuUserResponse> findListLichDoi(Integer timeChoPhepDoiLich, String idNguoiDat) {
        try {
            return doiLichNhieuUserReponsitory.findListLichDoi(timeChoPhepDoiLich, idNguoiDat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<DoiLichNhieuStaffReponse> findListLichDoiDatHo(Integer timeChoPhepDoiLich, Pageable pageable) {
        try {
            return doiLichNhieuUserReponsitory.findListLichDoiDatHo(timeChoPhepDoiLich,pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<DoiLichNhieuStaffReponse> tinKiemLichDatHoTheoSDT(Integer timeChoPhepDoiLich, String soDienThoai, Pageable pageable) {
        try {
            return doiLichNhieuUserReponsitory.tinKiemLichDatHoTheoSDT(timeChoPhepDoiLich, soDienThoai,pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
