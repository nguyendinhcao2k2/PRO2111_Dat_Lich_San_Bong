package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.ThoiGianDLUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ThoiGianDLUserServiver;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class ThoiGianDLUserServiceImpl implements ThoiGianDLUserServiver {

    @Autowired
    private ThoiGianDLUserRepository thoiGianDLUserRepository;

    @Override
    public void saveThoiGianDatLich(ThoiGianDatLich thoiGianDatLich) {
        thoiGianDLUserRepository.save(thoiGianDatLich);
    }

    @Override
    public void saveAllThoiGianDatLich(List list) {
        thoiGianDLUserRepository.saveAll(list);
    }
}
