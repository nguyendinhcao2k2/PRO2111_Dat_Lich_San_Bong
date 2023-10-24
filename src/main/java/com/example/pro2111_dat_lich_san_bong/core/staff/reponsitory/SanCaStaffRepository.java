package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanCaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caodinh
 */
public interface SanCaStaffRepository extends JpaRepository<SanCa, String> {

    @Query(value = "SELECT sc.id as idSanCa,c.thoi_gian_bat_dau as thoiGianBatDau,sb.id as idSanBong,sc.trang_thai as trangThai FROM san_ca as sc inner join san_bong as sb" +
            " on sc.id_san_bong = sb.id inner join ca as c on c.id = sc.id_ca where sc.thoi_gian_dat " +
            " between ?1 and ?2", nativeQuery = true)
    List<SanCaStaffResponse> findAllByDate(LocalDateTime from, LocalDateTime to);
}
