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
    public List<LuatSanResponse> getAll() {
        try {
            List<LuatSan> listLuatSan = luatSanAdminRepository.findAll();
            TypeToken<List<LuatSanResponse>> token = new TypeToken<>() {
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
        LuatSan luatSan = mapperLuatSan(luatSanRequest);
        luatSan.setTrangThai(0);
        luatSanAdminRepository.save(luatSan);
    }

    @Override
    public void delete(String id) {
        luatSanAdminRepository.deleteById(id);
    }

    @Override
    public void update(LuatSanRequest luatSanRequest) {
        luatSanAdminRepository.saveAndFlush(mapperLuatSan(luatSanRequest));
    }

    @Override
    public LuatSanResponse findById(String id) {
        try {
            if (luatSanAdminRepository.existsById(id)) {
                return modelMapper.map(luatSanAdminRepository.findById(id).get(), LuatSanResponse.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
