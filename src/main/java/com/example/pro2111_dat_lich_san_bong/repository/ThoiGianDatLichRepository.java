package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thepvph20110
 */

public interface ThoiGianDatLichRepository extends JpaRepository<ThoiGianDatLich, String> {
}
