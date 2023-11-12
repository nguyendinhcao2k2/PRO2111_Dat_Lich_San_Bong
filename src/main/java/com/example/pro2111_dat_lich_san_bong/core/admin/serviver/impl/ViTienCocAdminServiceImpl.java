package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ViTienCocAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ViTienCocAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ViTienCocAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ViTienCocAdminServiceImpl implements ViTienCocAdminService {

    @Autowired
    private ViTienCocAdminRepository viTienCocAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ViTienCocAdminRespone> getAll(Pageable pageable) {
        try {
            Page<ViTienCoc> viTienCocs = viTienCocAdminRepository.findAll(pageable);
            TypeToken<Page<ViTienCocAdminRespone>> token = new TypeToken<Page<ViTienCocAdminRespone>>() {
            };
            return modelMapper.map(viTienCocs, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(ViTienCocAdminRequest viTienCocAdminRequest) {
        try {
            ViTienCoc viTienCoc = modelMapper.map(viTienCocAdminRequest, ViTienCoc.class);
            viTienCocAdminRepository.save(viTienCoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (viTienCocAdminRepository.existsById(id)) {
                viTienCocAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ViTienCocAdminRequest viTienCocAdminRequest) {
        try {
            viTienCocAdminRepository.saveAndFlush(modelMapper.map(viTienCocAdminRequest, ViTienCoc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
