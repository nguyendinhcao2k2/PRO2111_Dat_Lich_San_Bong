package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LuatSanRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.HoaDonResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.LuatSanAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LuatSanAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.LuatSan;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuatSanAdminServiceImpl implements LuatSanAdminService {
    @Autowired
    private LuatSanAdminRepository luatSanAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    private LuatSan mapperLuatSan(LuatSanRequest luatSanRequest) {
        return modelMapper.map(luatSanRequest, LuatSan.class);

    }

    @Override
    public Page<LuatSanResponse> getAll(Pageable pageable) {
        try {
            Page<LuatSan> listLuatSan = luatSanAdminRepository.findAll(pageable);
            TypeToken<Page<LuatSanResponse>> token = new TypeToken<Page<LuatSanResponse>>() {
            };
            return modelMapper.map(listLuatSan, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void createLuatSan(LuatSanRequest luatSanRequest) {
        luatSanAdminRepository.save(mapperLuatSan(luatSanRequest));
    }

    @Override
    public void delete(String id) {
        luatSanAdminRepository.deleteById(id);
    }

    @Override
    public void update( LuatSanRequest luatSanRequest) {
        luatSanAdminRepository.saveAndFlush(mapperLuatSan(luatSanRequest));
    }
}
