package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanBongStaffResponse;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author caodinh
 */
public interface SanBongStaffRepository extends JpaRepository<SanBong, String> {

    @Query(value = "SELECT sb.id as idSanBong,sb.ten_san_bong as tenSanBong,sb.gia_san as giaSan " +
            ",ls.id as idLoaiSan,ls.ten_loai_san as tenLoaiSan " +
            " FROM san_bong sb " +
            "  inner join loai_san ls on sb.id_loai_san = ls.id order by sb.ten_san_bong", nativeQuery = true)
    List<SanBongStaffResponse> getAllSanBong();

    @Query(value = "SELECT sb.id as idSanBong, sb.ten_san_bong as tenSanBong, sb.gia_san as giaSan, ls.id as idLoaiSan, ls.ten_loai_san as tenLoaiSan " +
            "FROM san_bong sb " +
            "INNER JOIN loai_san ls ON sb.id_loai_san = ls.id " +
            "WHERE  sb.id = :idSanBong " +
            "ORDER BY sb.ten_san_bong", nativeQuery = true)
    List<SanBongStaffResponse> filterSanBong(@Param("idSanBong") String idSanBong);

}
