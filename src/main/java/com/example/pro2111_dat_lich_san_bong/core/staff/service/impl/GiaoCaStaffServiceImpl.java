package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.GiaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.GiaoCaStaffReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.GiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiaoCaStaffServiceImpl implements GiaoCaStaffService {

    @Autowired
    private GiaoCaStaffReponsitory giaoCaStaffReponsitory;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public GiaoCaRequest khoiTaoCaLam(GiaoCaRequest giaoCaRequest) {
        GiaoCa giaoCa = mapper.map(giaoCaRequest, GiaoCa.class);
        giaoCaStaffReponsitory.save(giaoCa);
        return giaoCaRequest;
    }
}
