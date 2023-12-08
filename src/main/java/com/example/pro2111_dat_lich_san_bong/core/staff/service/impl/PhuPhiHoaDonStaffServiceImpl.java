package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IPhuPhiHoaDonStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuPhiHoaDonStaffServiceImpl implements IPhuPhiHoaDonStaffService {

    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;

    @Override
    public List<PhuPhiHoaDonRequest> getPhuPhiHoaDonByIdSanCa(String idHoaDonSanCa, Integer trangThai) {
        try {
            return phuPhiHoaDonStaffRepository.getPhuPhiHoaDonByIdSanCa(idHoaDonSanCa,trangThai);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
