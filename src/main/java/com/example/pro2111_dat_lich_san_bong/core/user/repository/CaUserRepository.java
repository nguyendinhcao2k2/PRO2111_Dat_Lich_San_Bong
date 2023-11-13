package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
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
                        c.gia_ca + (select ls.gia_san from loai_san ls where ls.id =:idLoaiSan ) as 'giaSanCa',
                        (select count(*)from san_bong sb where sb.trang_thai = 0 and sb.id_loai_san= :idLoaiSan) as 'countSanBong'
                FROM ca c
                order by c.ten_ca asc
            """, nativeQuery = true)
    List<CaUserResponse> getAllCaByIdLoaiSan(@Param("idLoaiSan") String idLoaiSan);

    @Query(value = """
                SELECT
                        c.id,
                        c.ten_ca as 'tenCa',
                        c.thoi_gian_bat_dau as 'thoiGianBatDau',
                        c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
                        c.gia_ca + (select ls.gia_san from loai_san ls where ls.id =:idLoaiSan ) as 'giaSanCa',
                        (select count(*)from san_bong sb where sb.trang_thai = 0 and sb.id_loai_san= :idLoaiSan) as 'countSanBong'
                FROM ca c
                where c.id =:req
                order by c.ten_ca asc
            """, nativeQuery = true)
    CaUserResponse getCaByIdCa(@Param("req")String idCa ,@Param("idLoaiSan") String idLoaiSan );

    Ca findFirstByTenCa(String tenCa);
}
