package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.PhuPhiHoaDonAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiHoaDonAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.PhuPhiAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.PhuPhiHoaDonAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.PhuPhiHoaDonAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PhuPhiHoaDonAdminServiceImpl implements PhuPhiHoaDonAdminService {

    @Autowired
    private PhuPhiHoaDonAdminRepository phuPhiHoaDonAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<PhuPhiHoaDonAdminRespone> getAll(Pageable pageable) {
        try {
            Page<PhuPhiHoaDon> phuPhiHoaDons = phuPhiHoaDonAdminRepository.findAll(pageable);
            TypeToken<Page<PhuPhiHoaDonAdminRespone>> token = new TypeToken<Page<PhuPhiHoaDonAdminRespone>>() {
            };
            return modelMapper.map(phuPhiHoaDons, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(PhuPhiHoaDonAdminRequest phuPhiHoaDonAdminRequest) {
        try {
            PhuPhiHoaDon phuPhiHoaDon = modelMapper.map(phuPhiHoaDonAdminRequest, PhuPhiHoaDon.class);
            phuPhiHoaDonAdminRepository.save(phuPhiHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (phuPhiHoaDonAdminRepository.existsById(id)) {
                phuPhiHoaDonAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PhuPhiHoaDonAdminRequest phuPhiHoaDonAdminRequest) {
        try {
            phuPhiHoaDonAdminRepository.saveAndFlush(modelMapper.map(phuPhiHoaDonAdminRequest, PhuPhiHoaDon.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
