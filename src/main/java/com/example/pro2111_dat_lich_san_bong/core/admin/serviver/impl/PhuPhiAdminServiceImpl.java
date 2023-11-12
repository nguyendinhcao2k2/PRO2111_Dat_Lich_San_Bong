package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.PhuPhiAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.PhuPhiAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.PhuPhiAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PhuPhiAdminServiceImpl implements PhuPhiAdminService {

    @Autowired
    private PhuPhiAdminRepository phuPhiAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<PhuPhiAdminResponse> getAll(Pageable pageable) {
        try {
            Page<PhuPhi> phuPhiPage = phuPhiAdminRepository.findAll(pageable);
            TypeToken<Page<PhuPhiAdminResponse>> token = new TypeToken<Page<PhuPhiAdminResponse>>() {
            };
            return modelMapper.map(phuPhiPage, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(PhuPhiAdminRequest phuPhiAdminRequest) {
        try {
            PhuPhi phuPhi = modelMapper.map(phuPhiAdminRequest, PhuPhi.class);
            phuPhiAdminRepository.save(phuPhi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (phuPhiAdminRepository.existsById(id)) {
                phuPhiAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PhuPhiAdminRequest phuPhiAdminRequest) {
        try {
            phuPhiAdminRepository.saveAndFlush(modelMapper.map(phuPhiAdminRequest, PhuPhi.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
