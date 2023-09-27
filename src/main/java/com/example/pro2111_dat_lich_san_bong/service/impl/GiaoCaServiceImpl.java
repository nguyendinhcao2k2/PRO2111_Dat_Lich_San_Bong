package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import com.example.pro2111_dat_lich_san_bong.service.IGiaoCaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiaoCaServiceImpl implements IGiaoCaService {

    @Autowired
    GiaoCaRepository giaoCaRepository;

    @Override
    public GiaoCa findGiaoCaByTrangThai(Integer trangThai) {
        return giaoCaRepository.findGiaoCaByTrangThai(trangThai);
    }

    @Override
    public Boolean khoiTaoCaLam(GiaoCa giaoCa) {
        return false;
    }
}
