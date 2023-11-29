package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */

public interface SanCaUserRepository extends SanCaRepository {

    @Query(value = """
        select sc.id, sc.id_san_bong as 'idSanBong',
        sc.gia,sc.trang_thai as'trangThai',
        c.ten_ca as'tenCa', c.thoi_gian_bat_dau as 'thoiGianBatDau',
        c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
        sc.ngay_den_san as 'thoiGianDat',
        hd_sc.dem_so_lan_doi_lich as 'soLanDoiLich'
        from san_ca sc 
        join hoa_don_san_ca hd_sc on hd_sc.id_san_ca = sc.id
        join ca c on sc.id_ca = c.id
        join san_bong sb on sb.id = sc.id_san_bong
        where sc.user_id =:#{#request.idAcount} 
        and sb.id_loai_san = :#{#request.idLoaiSan} 
        order by c.ten_ca asc
    """,nativeQuery = true)
    List<SanCaUserResponse> getAllSanCa(@Param("request") EventRequest request);

    @Query(value = """
        SELECT COUNT(*)
        FROM san_ca
        WHERE SUBSTRING(id, INSTR(id, "+") + 1) =:request
    """,nativeQuery = true)
    int getAllSanCa(@Param("request") String textCheck);

    SanCa findAllById(String id);

    @Modifying
    @Query(value = """
        delete from san_ca sc where sc.id =:request
    """,nativeQuery = true)
    void deleteSanCaById(@Param("request")String idSanCa);

}
