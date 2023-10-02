package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */

public interface SanCaUserRepository extends SanCaRepository {

    @Query(value = """
        select sc.id_san_bong as 'idSanBong',
        sc.gia,sc.trang_thai as'trangThai',
        c.ten_ca as'tenCa', c.thoi_gian_bat_dau as 'thoiGianBatDau',
        c.thoi_gian_ket_thuc as 'thoiGianKetThuc'
        from san_ca sc join ca c on sc.id_ca = c.id
        where Date(sc.thoi_gian_tao) = STR_TO_DATE(:#{#req.ngayTaoSanCa}, '%d/%m/%Y') ;
    """,countQuery = """
        select count(*)
        from san_ca sc join ca c on sc.id_ca = c.id
        where Date(sc.thoi_gian_tao) = STR_TO_DATE(:#{#req.ngayTaoSanCa}, '%d/%m/%Y') ;
    """,nativeQuery = true)
    List<SanCaUserRepository> getAllSanCa(@Param("req") SanCaUserRequest req);
}
