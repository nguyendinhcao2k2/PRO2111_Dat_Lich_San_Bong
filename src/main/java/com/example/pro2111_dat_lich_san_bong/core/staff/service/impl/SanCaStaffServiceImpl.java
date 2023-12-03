package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.EventStaffRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.ISanCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */

@Service
public class SanCaStaffServiceImpl implements ISanCaStaffService {

    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;


    @Override
    public List getAllSanCa(EventStaffRequest request) {
        return sanCaStaffRepository.getAllSanCa(request);
    }

    @Override
    public int checkSanCa(String idLoaiSan, String idCa, String ngayDat) {
        return 0;
    }

    @Override
    public List checkListSanCa(List<CheckSanCaUserRequest> list) {
        return null;
    }

    @Override
    public void saveAllSanCa(List list) {

    }

    @Override
    public SanCa findSanCaById(String id) {
        return null;
    }

    @Override
    public void deleteSanCaById(String idSanCa) {

    }

    @Override
    public void saveSanCa(SanCa sanCa) {

    }
}
