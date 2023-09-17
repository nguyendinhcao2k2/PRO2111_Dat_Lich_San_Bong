package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoCaRepository extends JpaRepository<GiaoCa, String> {
    GiaoCa findGiaoCaByTrangThai(Integer trangThai);
}
