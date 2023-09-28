package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.request.GiaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import com.example.pro2111_dat_lich_san_bong.service.IGiaoCaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@Service
public class GiaoCaServiceImpl implements IGiaoCaService {

    @Autowired
    GiaoCaRepository giaoCaRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public GiaoCa findGiaoCaByTrangThai(Integer trangThai) {
        return giaoCaRepository.findGiaoCaByTrangThai(trangThai);
    }

    @Override
    @Transactional
    public Boolean khoiTaoCaLam(GiaoCaRequest giaoCaRequest) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GiaoCa giaoCa = mapper.map(giaoCaRequest, GiaoCa.class);
            giaoCaRepository.save(giaoCa);
            System.out.println(giaoCa);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
