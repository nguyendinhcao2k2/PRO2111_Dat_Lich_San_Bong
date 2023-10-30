package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author caodinh
 */
public interface HoaDonSanCaStaffRepository extends JpaRepository<HoaDonSanCa, String> {

    @Query(value = "SELECT hdsc.id_san_ca FROM hoa_don_san_ca hdsc where hdsc.id_hoa_don = ?1", nativeQuery = true)
    List<String> findIdSanCaByIdHoaDon(String idHoaDon);

    @Modifying
    @Query(value = "DELETE FROM hoa_don_san_ca hdsc WHERE hdsc.id_hoa_don = ?1", nativeQuery = true)
    void deleteByIdHoaDon(String idHoaDon);

}
