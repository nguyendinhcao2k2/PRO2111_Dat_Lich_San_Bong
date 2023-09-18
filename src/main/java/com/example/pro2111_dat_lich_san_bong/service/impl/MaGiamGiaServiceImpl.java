package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.MaGiamGia;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.MaGiamGiaRequest;
import com.example.pro2111_dat_lich_san_bong.repository.MaGiamGiaRepository;
import com.example.pro2111_dat_lich_san_bong.service.MaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaGiamGiaServiceImpl implements MaGiamGiaService {

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;


    @Override
    public List<MaGiamGia> finAll() {
        return maGiamGiaRepository.findAll();
    }

    @Override
    public Optional<MaGiamGia> findById(String id) {
        return maGiamGiaRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        maGiamGiaRepository.deleteById(id);
    }

    @Override
    public MaGiamGia update(String id, MaGiamGiaRequest maGiamGiaRequest) {
        return maGiamGiaRepository.findById(id)
                .map(maGiamGiaNew ->{
                    maGiamGiaNew.setTenMaGiamGia(maGiamGiaRequest.getTenMaGiamGia());
                    maGiamGiaNew.setPhanTram(maGiamGiaRequest.getPhanTram());
                    maGiamGiaNew.setNgayBatDau(maGiamGiaRequest.getNgayBatDau());
                    maGiamGiaNew.setNgayKetThuc(maGiamGiaRequest.getNgayKetThuc());
                    maGiamGiaNew.setTrangThai(maGiamGiaRequest.getTrangThai());
                    maGiamGiaNew.setIdKhachHang(maGiamGiaRequest.getIdKhachHang());
                    maGiamGiaNew.setIdAccount(maGiamGiaRequest.getIdAccount());
                    return maGiamGiaRepository.save(maGiamGiaNew);
                }).orElseThrow(
                        () -> new RestApiException("Not Found Chuc Vu Width Id : " + id)
                );
    }

    @Override
    public MaGiamGia create(MaGiamGiaRequest maGiamGiaRequest) {
        return maGiamGiaRepository.save(
                MaGiamGia.builder()
                        .phanTram(maGiamGiaRequest.getPhanTram())
                        .tenMaGiamGia(maGiamGiaRequest.getTenMaGiamGia())
                        .ngayBatDau(maGiamGiaRequest.getNgayBatDau())
                        .ngayKetThuc(maGiamGiaRequest.getNgayKetThuc())
                        .trangThai(maGiamGiaRequest.getTrangThai())
                        .idKhachHang(maGiamGiaRequest.getIdKhachHang())
                        .idAccount(maGiamGiaRequest.getIdAccount())
                        .build()
        );
    }
}
