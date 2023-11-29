package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;


import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.HoaDonResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.HoaDonAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HoaDonAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.mapper.ModelMapperConfig;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonAdminServiceImpl implements HoaDonAdminService {

    @Autowired
    private HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    private ModelMapperConfig modelMapperConfig;

    @Override
    public List<HoaDonResponse> getAll() {
        try {
            List<HoaDon> listHoaDon = hoaDonAdminRepository.findAll();
            TypeToken<List<HoaDonResponse>> token = new TypeToken<List<HoaDonResponse>>() {
            };
            return modelMapperConfig.mapper().map(listHoaDon, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }

    }
}
