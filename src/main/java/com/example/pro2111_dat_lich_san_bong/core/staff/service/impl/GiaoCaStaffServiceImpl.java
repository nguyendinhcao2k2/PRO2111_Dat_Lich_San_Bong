package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.GiaoCaStaffReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiaoCaStaffServiceImpl implements IGiaoCaStaffService {

    @Autowired
    private GiaoCaStaffReponsitory _giaoCaStaffReponsitory;
    private ModelMapper _mapper = new ModelMapper();

    @Override
    public Boolean khoiTaoCaLamViec(KhoiTaoCaRequest khoiTaoCaRequest) {
        try {
            GiaoCa giaoCa = _mapper.map(khoiTaoCaRequest, GiaoCa.class);
            _giaoCaStaffReponsitory.save(giaoCa);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
