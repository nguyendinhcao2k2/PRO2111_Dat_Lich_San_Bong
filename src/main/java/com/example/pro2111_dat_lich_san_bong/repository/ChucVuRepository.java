package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author caodinh
 */
public interface ChucVuRepository extends JpaRepository<ChucVu, String> {

    @Query("select cv from ChucVu cv where cv.tenChucVu = :tenChucVu")
    ChucVu findByTenChucVu(@Param("tenChucVu") String tenChucVu);

}
