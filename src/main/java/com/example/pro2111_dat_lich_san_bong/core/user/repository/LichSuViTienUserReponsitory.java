package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuViTien;
import com.example.pro2111_dat_lich_san_bong.repository.LichSuViTienRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuViTienUserReponsitory extends LichSuViTienRepository {
    LichSuViTien findAllByIdViTienCoc(String idViTienCoc);
}
