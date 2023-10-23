package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author caodinh
 */
public interface CaStaffRepository extends JpaRepository<Ca, String> {

    @Query(value = "SELECT ca.id as idCa,ca.ten_ca as tenCa,ca.gia_ca as giaCa,ca.thoi_gian_bat_dau as thoiGianBatDau, " +
            "  ca.thoi_gian_ket_thuc as thoiGianKetThuc " +
            "  FROM ca as ca order by ca.ten_ca",nativeQuery = true)
    List<CaStaffResponse> getCa();
}
