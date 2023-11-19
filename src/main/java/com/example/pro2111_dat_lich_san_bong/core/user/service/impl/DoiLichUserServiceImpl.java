package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.DoiLichUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.DoiLichUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class DoiLichUserServiceImpl implements DoiLichUserService {

    @Autowired
    private DoiLichUserReponsitory doiLichUserReponsitory;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private SanBongUserService sanBongUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Override
    public DoiLichOneUserResponse findFirstByIdSanCa(String idSanCa) {
        try {
            HoaDonSanCa hoaDonSanCa = doiLichUserReponsitory.findFirstByIdSanCa(idSanCa);
            if (hoaDonSanCa == null) {
                return null;
            }
            DoiLichOneUserResponse response = mapper.map(hoaDonSanCa, DoiLichOneUserResponse.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DoiLichOneUserResponse findFirstByIdSanCaAndTrangThai(Integer trangThaiHdsc, String idSanCa) {
        try {
            DoiLichOneUserResponse response = doiLichUserReponsitory.findFirstByIdSanCaAndTrangThai(trangThaiHdsc, idSanCa);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean demNgayCheck(long value) {
        SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_DOI_LICH);
        if (value >= Integer.valueOf(param.getValue())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNgayDenSan(LocalDate localDate) {
        try {
            LocalDate today = LocalDate.now();
//            ngay den san lon hon ngay hom nay
            if (today.isBefore(localDate)) {
                long dayBetween = ChronoUnit.DAYS.between(today, localDate);
                return demNgayCheck(dayBetween);
            }
//            ngay den san be hon ngay hom nay
            else if (today.isAfter(localDate)) {
                long dayBetween = ChronoUnit.DAYS.between(localDate, today);
                return demNgayCheck(dayBetween);
            }
//        ngay den san = ngay hom nay
            return false;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getIdSanBongEmpty(String textCheck, String idLoaiSan) {
        String idSanBong = "";
        List<SanBong> list = sanBongUserService.getALlSanBongByIdLoaiSan(idLoaiSan);

        for (SanBong sanBong : list) {
            String idSanCa = sanBong.getId() + "+" + textCheck;
            SanCa sanCa = sanCaUserService.findSanCaById(idSanCa);

            if (sanCa == null || Objects.isNull(sanCa)) {
                idSanBong = sanBong.getId();
                break; // Exit the loop
            }
        }

        return idSanBong;
    }

}
