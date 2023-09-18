package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.ChucVuRequest;
import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
import com.example.pro2111_dat_lich_san_bong.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChucVuServieImpl implements ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVu> finAll() {
        return chucVuRepository.findAll();
    }

    @Override
    public Optional<ChucVu> findById(String id) {
        return chucVuRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        chucVuRepository.deleteById(id);
    }

    @Override
    public ChucVu update(String id, ChucVuRequest chucVuRequest) {
        Optional<ChucVu> chucVu = chucVuRepository.findById(id);
        return chucVu.map(chucVuNew -> {
                    chucVuNew.setTenChucVu(chucVuRequest.getTenChucVu());
                    chucVuNew.setTrangThai(chucVuRequest.getTrangThai());
                    return chucVuRepository.save(chucVuNew);
                }
        ).orElseThrow(
                () -> new RestApiException("Not Found Chuc Vu Width Id : " + id)
        );
    }

    @Override
    public ChucVu create(ChucVuRequest chucVuRequest) {
        return chucVuRepository.save(
                ChucVu.builder()
                        .tenChucVu(chucVuRequest.getTenChucVu())
                        .trangThai(chucVuRequest.getTrangThai())
                        .build()
        );
    }
}
