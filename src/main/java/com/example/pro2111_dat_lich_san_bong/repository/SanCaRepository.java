package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.request.SanCaJobRequest;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author caodinh
 */
public interface SanCaRepository extends JpaRepository<SanCa, String> {

    @Query(value = """
        SELECT sc.*
        FROM san_ca sc
        WHERE
        sc.id_ca = :#{#req.idCa} and
        sc.ngay_den_san = STR_TO_DATE(:#{#req.ngayTao}, '%d/%m/%Y')
          AND sc.trang_thai = :#{#req.trangThai}
    """,nativeQuery = true)
    List<SanCa> findAllSanCaByIdCa(@Param("req") SanCaJobRequest req);
}
