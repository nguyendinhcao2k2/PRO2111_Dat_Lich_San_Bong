package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface CaUserRepository extends CaRepository {

    @Query(value = """
        SELECT
                c.id,
                c.ten_ca as 'tenCa',
                c.thoi_gian_bat_dau as 'thoiGianBatDau',
                c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
                c.gia_ca + (select ls.gia_san from loai_san ls where ls.id =:idLoaiSan ) as 'giaSanCa'
        FROM ca c
        order by c.ten_ca asc
    """,nativeQuery = true)
    List<CaUserResponse> getAllCaByIdLoaiSan(@Param("idLoaiSan") String idLoaiSan);

}
