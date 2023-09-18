package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.BienDongVi;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.BienDongViRequest;
import com.example.pro2111_dat_lich_san_bong.repository.BienDongViRepository;
import com.example.pro2111_dat_lich_san_bong.service.BienDongViService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BienDongViServiceImpl implements BienDongViService {
    @Autowired
    private BienDongViRepository bienDongViRepository;

    @Override
    public List<BienDongVi> finAll() {
        return bienDongViRepository.findAll();
    }

    @Override
    public Optional<BienDongVi> findById(String id) {
        return bienDongViRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        bienDongViRepository.deleteById(id);
    }

    @Override
    public BienDongVi update(String id, BienDongViRequest bienDongViRequest) {
        return bienDongViRepository.findById(id)
                .map(bienDongViNew -> {
                    bienDongViNew.setNguoiNhan(bienDongViRequest.getNguoiNhan());
                    bienDongViNew.setSoTien(bienDongViRequest.getSoTien());
                    bienDongViNew.setLoaiTien(bienDongViRequest.getLoaiTien());
                    bienDongViNew.setTrangThai(bienDongViRequest.getTrangThai());
                    bienDongViNew.setThoiGian(bienDongViRequest.getThoiGian());
                    bienDongViNew.setLoaiBienDong(bienDongViRequest.getLoaiBienDong());
                    bienDongViNew.setTaiKhoanVi(bienDongViRequest.getTaiKhoanVi());
                    return bienDongViRepository.save(bienDongViNew);
                }).orElseThrow(
                        () -> new RestApiException("Not Found Bien Dong Vi Width Id : " + id)
                );
    }

    @Override
    public BienDongVi create(BienDongViRequest bienDongViRequest) {
        return bienDongViRepository.save(
                BienDongVi.builder()
                        .nguoiNhan(bienDongViRequest.getNguoiNhan())
                        .soTien(bienDongViRequest.getSoTien())
                        .loaiBienDong(bienDongViRequest.getLoaiBienDong())
                        .loaiTien(bienDongViRequest.getLoaiTien())
                        .trangThai(bienDongViRequest.getTrangThai())
                        .thoiGian(bienDongViRequest.getThoiGian())
                        .taiKhoanVi(bienDongViRequest.getTaiKhoanVi())
                        .build()
        );
    }
}
