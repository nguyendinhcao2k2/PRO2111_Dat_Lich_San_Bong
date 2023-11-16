package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DichVuSanBongAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DichVuSanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.DichVuSanBongAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DichVuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DichVuSanBongAdminServiceImpl implements DichVuSanBongAdminService {

    @Autowired
    private DichVuSanBongAdminRepository dichVuSanBongAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<DichVuSanBongAdminRespone> getAll(Pageable pageable) {
        try {
            Page<DichVuSanBong> dichVuSanBongs = dichVuSanBongAdminRepository.findAll(pageable);
            TypeToken<Page<DichVuSanBongAdminRespone>> token = new TypeToken<Page<DichVuSanBongAdminRespone>>() {
            };
            return modelMapper.map(dichVuSanBongs, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(DichVuSanBongAdminRequest dichVuSanBongAdminRequest) {
        try {
            DichVuSanBong dichVuSanBong = modelMapper.map(dichVuSanBongAdminRequest, DichVuSanBong.class);
            dichVuSanBongAdminRepository.save(dichVuSanBong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (dichVuSanBongAdminRepository.existsById(id)) {
                dichVuSanBongAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DichVuSanBongAdminRequest dichVuSanBongAdminRequest) {
        try {
            dichVuSanBongAdminRepository.saveAndFlush(modelMapper.map(dichVuSanBongAdminRequest, DichVuSanBong.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
