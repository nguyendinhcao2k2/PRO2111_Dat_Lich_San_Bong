package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import org.hibernate.annotations.Parameter;
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
        c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
        sc.thoi_gian_dat as 'thoiGianDat'
        from san_ca sc 
        join ca c on sc.id_ca = c.id
        join san_bong sb on sb.id = sc.id_san_bong
        where sc.user_id =:#{#request.idAcount} 
        and sb.id_loai_san = :#{#request.idLoaiSan} 
        order by c.ten_ca asc
    """,nativeQuery = true)
    List<SanCaUserResponse> getAllSanCa(@Param("request") EventRequest request);
}
