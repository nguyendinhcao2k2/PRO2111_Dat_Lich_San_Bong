package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ChucVuAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ChucVuAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ChucVuAdminRespository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ChucVuAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChucVuAdminServiceImpl implements ChucVuAdminService {

    @Autowired
    private ChucVuAdminRespository chucVuAdminRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ChucVuAdminRespone> getAll(Pageable pageable) {

        try {
            Page<ChucVu> listChucVu = chucVuAdminRespository.findAll(pageable);
            TypeToken<Page<ChucVuAdminRespone>> token = new TypeToken<Page<ChucVuAdminRespone>>() {
            };
            return modelMapper.map(listChucVu, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(ChucVuAdminRequest chucVuAdminRequest) {
        try {
            ChucVu chucVu = modelMapper.map(chucVuAdminRequest, ChucVu.class);
            chucVuAdminRespository.save(chucVu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (chucVuAdminRespository.existsById(id)) {
                chucVuAdminRespository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ChucVuAdminRequest chucVuAdminRequest) {
        try {
            chucVuAdminRespository.saveAndFlush(modelMapper.map(chucVuAdminRequest, ChucVu.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
