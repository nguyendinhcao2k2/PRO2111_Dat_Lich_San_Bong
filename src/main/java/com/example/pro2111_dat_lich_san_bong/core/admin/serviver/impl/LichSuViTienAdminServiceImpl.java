package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LichSuViTienAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LichSuViTienAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.LichSuViTienAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuViTienAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuViTien;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuViTienAdminServiceImpl implements LichSuViTienAdminService {
    @Autowired
    private LichSuViTienAdminRepository lichSuViTienAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LichSuViTienAdminRespone> listAll() {
            try {
                List<LichSuViTien> lichSuViTiens = lichSuViTienAdminRepository.findAll();
                TypeToken<List<LuatSanResponse>> token = new TypeToken<>() {
                };
                return modelMapper.map(lichSuViTiens, token.getType());
            } catch (
                    Exception e
            ) {
                return null;
            }
    }

    @Override
    public Page<LichSuViTienAdminRespone> getAll(Pageable pageable) {
        try {
            Page<LichSuViTien> lichSuViTiens = lichSuViTienAdminRepository.findAll(pageable);
            TypeToken<Page<LichSuViTienAdminRespone>> token = new TypeToken<Page<LichSuViTienAdminRespone>>() {
            };
            return modelMapper.map(lichSuViTiens, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(LichSuViTienAdminRequest lichSuViTienAdminRequest) {
        try {
            LichSuViTien lichSuViTien = modelMapper.map(lichSuViTienAdminRequest, LichSuViTien.class);
            lichSuViTienAdminRepository.save(lichSuViTien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (lichSuViTienAdminRepository.existsById(id)) {
                lichSuViTienAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LichSuViTienAdminRequest lichSuViTienAdminRequest) {
        try {
            lichSuViTienAdminRepository.saveAndFlush(modelMapper.map(lichSuViTienAdminRequest, LichSuViTien.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
