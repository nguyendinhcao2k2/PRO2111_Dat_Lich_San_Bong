package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.LoaiSanStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.LoaiSanStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class LoaiSanStaffServiceImpl implements LoaiSanStaffService {

    @Autowired
    private LoaiSanStaffRepository loaiSanStaffRepository;

    @Override
    public List<LoaiSan> getAllLoaiSan() {
        return loaiSanStaffRepository.getAllLoaiSan();
    }
}
