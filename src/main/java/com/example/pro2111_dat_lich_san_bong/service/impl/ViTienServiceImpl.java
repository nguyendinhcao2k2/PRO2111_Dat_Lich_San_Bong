package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.ViTien;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;
import com.example.pro2111_dat_lich_san_bong.service.ViTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViTienServiceImpl implements ViTienService {
    @Autowired
    private ViTienRepository viTienRepository;

    @Override
    public List<ViTien> getAllViTiens() {
        return viTienRepository.findAll();
    }

    @Override
    public ViTien getOneViTien(String id) {
        if (viTienRepository.existsById(id)) {
            return viTienRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewViTien(ViTien viTien) {
        if (!viTienRepository.existsById(viTien.getId())) {
            viTienRepository.save(viTien);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateViTien(String id, ViTien viTien) {
        if (viTienRepository.existsById(id)) {
            viTienRepository.save(viTien);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteViTien(String id) {
        if (viTienRepository.existsById(id)) {
            viTienRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
