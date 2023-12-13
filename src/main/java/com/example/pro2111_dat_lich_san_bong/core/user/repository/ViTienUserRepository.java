package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author thepvph20110
 */
public interface ViTienUserRepository extends ViTienRepository {

    @Query("""
        SELECT vt
        from ViTienCoc vt 
        where vt.idHoaDon = :idHoaDon
        order by vt.thoiGianTao desc
        LIMIT 1
""")
    ViTienCoc findFirstByIdHoaDon(@Param("idHoaDon") String idHoaDon);
}
