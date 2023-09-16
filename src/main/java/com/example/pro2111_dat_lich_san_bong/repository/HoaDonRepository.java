package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author caodinh
 */
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
}
