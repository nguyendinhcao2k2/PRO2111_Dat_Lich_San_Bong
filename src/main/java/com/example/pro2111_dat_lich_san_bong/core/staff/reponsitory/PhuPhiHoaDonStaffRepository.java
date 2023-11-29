package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhuPhiHoaDonStaffRepository extends JpaRepository<PhuPhiHoaDon, String> {
    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest" +
            "(PPHD.id, PPHD.idHoaDonSanCa, PP.id, PP.tenPhuPhi, PP.giaPhuPhi, PPHD.trangThai) " +
            "FROM PhuPhiHoaDon PPHD JOIN PhuPhi PP ON PPHD.idPhuPhi = PP.id " +
            "WHERE PPHD.idHoaDonSanCa = :idHoaDonSanCa AND PPHD.trangThai = :trangThai")
    List<PhuPhiHoaDonRequest> getPhuPhiHoaDonByIdSanCa(@Param("idHoaDonSanCa") String idHoaDonSanCa, @Param("trangThai") int trangThai);

    List<PhuPhiHoaDon> findPhuPhiHoaDonsByIdHoaDonSanCa(String idHoaDonSanCa);
}
