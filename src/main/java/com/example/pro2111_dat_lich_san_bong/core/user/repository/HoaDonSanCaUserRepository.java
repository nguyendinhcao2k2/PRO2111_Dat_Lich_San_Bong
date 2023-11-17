package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface HoaDonSanCaUserRepository extends HoaDonSanCaReponsitory {

    List findAllByIdHoaDon(String idHoaDon);

    @Query(value = """
        delete from hoa_don_san_ca where id_hoa_don =:request
    """,nativeQuery = true)
    void deleteAllByIdHoaDon(@Param("request")String idHoaDon);
}
