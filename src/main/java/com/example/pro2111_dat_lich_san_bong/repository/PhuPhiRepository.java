package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author caodinh
 */
public interface PhuPhiRepository extends JpaRepository<PhuPhi, String> {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma_phu_phi, 5, 100) AS SIGNED)) \n" +
            "FROM dat_lich_san_bong.phu_phi", nativeQuery = true)
    Integer genCodePhuPhi();
}
