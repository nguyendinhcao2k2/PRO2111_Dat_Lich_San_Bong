package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuDichVuSuDungStaffReponse;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DichVuSanBongStaffRepository extends JpaRepository<DichVuSanBong, String> {
    List<DichVuSanBong> findAllByIdHoaDonSanCaAndTrangThai(String idHoaDonSanCa, int trangThai);

    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest" +
            "(dv.idHoaDonSanCa, dv.idNuocUong, dv.idDoThue, nu.image, dt.image, nu.tenNuocUong, dt.tenDoThue, nu.donGia, dt.donGia, SUM(dv.donGia), SUM(dv.soLuongDoThue), SUM(dv.soLuongNuocUong), dt.soLuong, nu.soLuong)" +
            "FROM DichVuSanBong dv " +
            "LEFT JOIN DoThue dt ON dt.id = dv.idDoThue " +
            "LEFT JOIN NuocUong nu ON nu.id = dv.idNuocUong " +
            "WHERE dv.idHoaDonSanCa = :idHoaDonSanCa AND dv.trangThai = :trangThai " +
            "GROUP BY dv.idDoThue, dv.idNuocUong")
    List<DichVuSanBongRequest> dichVuSanBongSuDungByHoaDonSanCas(@Param("idHoaDonSanCa") String idHoaDonSanCa, @Param("trangThai") int trangThai);

    @Query("""
        SELECT dvsb
        FROM  DichVuSanBong dvsb    
        where dvsb.idHoaDonSanCa = :idHoaDonSanCa
""")
    List<DichVuSanBong> findAllByLichSuSuDungDichVu(@Param("idHoaDonSanCa") String idHoaDonSanCa);
}