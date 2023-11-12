package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanCaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanCaAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.SanCaAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SanCaAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SanCaAdminServiceImpl implements SanCaAdminService {

    @Autowired
    private SanCaAdminRepository sanCaAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<SanCaAdminRespone> getAll(Pageable pageable) {
        try {
            Page<SanCa> sanCaPage = sanCaAdminRepository.findAll(pageable);
            TypeToken<Page<SanCaAdminRespone>> token = new TypeToken<Page<SanCaAdminRespone>>() {
            };
            return modelMapper.map(sanCaPage, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    @Transactional
    public void create(SanCaAdminRequest sanCaAdminRequest) {
        try {
            SanCa sanCa = modelMapper.map(sanCaAdminRequest, SanCa.class);
            sanCaAdminRepository.save(sanCa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            if (sanCaAdminRepository.existsById(id)) {
                sanCaAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(SanCaAdminRequest sanCaAdminRequest) {
        try {
            sanCaAdminRepository.saveAndFlush(modelMapper.map(sanCaAdminRequest, SanCa.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
