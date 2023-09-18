package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.DichVu;
import com.example.pro2111_dat_lich_san_bong.repository.DichVuRepository;
import com.example.pro2111_dat_lich_san_bong.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DichVuServiceImpl implements DichVuService {
    @Autowired
    private DichVuRepository dichVuRepository;

    @Override
    public List<DichVu> getAllDichVu() {
        return dichVuRepository.findAll();
    }

    @Override
    public DichVu getOneDichVu(String id) {
        if (dichVuRepository.existsById(id)) {
            return dichVuRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewDichVu(DichVu dichVu) {
        if (!dichVuRepository.existsById(dichVu.getId())) {
            dichVuRepository.save(dichVu);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateDichVu(String id, DichVu dichVu) {
        if (dichVuRepository.existsById(id)) {
            dichVuRepository.save(dichVu);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteDichVu(String id) {
        if (dichVuRepository.existsById(id)) {
            dichVuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
