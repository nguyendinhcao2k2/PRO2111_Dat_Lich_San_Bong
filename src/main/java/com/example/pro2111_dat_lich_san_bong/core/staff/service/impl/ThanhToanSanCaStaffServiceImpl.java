package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IThanhToanSanCaStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanSanCaStaffServiceImpl implements IThanhToanSanCaStaffService {
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;

    @Override
    public List<HoaDonThanhToanRequest> getAllHoaDonSanCas(int trangThai) {
        return hoaDonSanCaStaffRepository.findAllByTrangThai(trangThai);
    }

    @Override
    public HoaDonThanhToanRequest getOneHoaDonThanhToan(String id) {
        return hoaDonSanCaStaffRepository.findOneById(id);
    }
}