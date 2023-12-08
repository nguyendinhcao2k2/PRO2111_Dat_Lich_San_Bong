package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonSanCaStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.HoaDonStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonSanCaStaffServiceImpl implements IHoaDonSanCaStaffService {

    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;

    @Override
    public List<LichSuHoaDonSanCaStaffReponse> findAllLichSuHoaDonSanCaTheoIdHD(String idHoaDon) {
        try {
            return hoaDonSanCaStaffRepository.findAllLichSuHoaDonSanCaTheoIdHD(idHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
