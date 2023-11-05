package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DichVuSanBongStaffServiceImpl implements IDichVuSanBongStaffService {
    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;

    @Override
    public List<DichVuSanBongRequest> dichVuSanBongSuDungByHoaDonSanCas(String idHoaDonSanCa, int trangThai) {
        return dichVuSanBongStaffRepository.dichVuSanBongSuDungByHoaDonSanCas(idHoaDonSanCa, trangThai);
    }
}
