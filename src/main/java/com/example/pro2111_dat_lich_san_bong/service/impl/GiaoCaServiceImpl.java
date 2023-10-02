package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.request.GiaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import com.example.pro2111_dat_lich_san_bong.service.IGiaoCaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@Service
public class GiaoCaServiceImpl implements IGiaoCaService {

    @Autowired
    GiaoCaRepository giaoCaRepository;



    @Override
    public GiaoCa findGiaoCaByTrangThai(Integer trangThai) {
        return giaoCaRepository.findGiaoCaByTrangThai(trangThai);
    }

    @Override
    @Transactional
    public Boolean khoiTaoCaLam(GiaoCa giaoCa) {
        try {
            giaoCaRepository.save(giaoCa);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public GiaoCa findGiaoCaGanNhat() {
        GiaoCa giaoCa = giaoCaRepository.findAll(Sort.by("thoiGianKetCa").descending()).get(0);
        if (giaoCa != null) {
            return giaoCa;
        }
        return null;
    }
}
