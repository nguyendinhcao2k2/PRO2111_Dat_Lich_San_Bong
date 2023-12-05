package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.ViTienStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IViTienStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class ViTienStaffSeviceImpl implements IViTienStaffService {

    @Autowired
    private ViTienStaffRepository viTienStaffRepository;


    @Override
    public ViTienCoc saveViTien(ViTienCoc viTienCoc) {
        return viTienStaffRepository.save(viTienCoc);
    }
}
