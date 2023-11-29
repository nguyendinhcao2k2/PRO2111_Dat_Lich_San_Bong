package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HoaDonSanCaViewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonSanCaAdminRepository extends JpaRepository<HoaDonSanCa, String> {
    Page<HoaDonSanCa> findByTrangThaiEquals(Integer trangThai, Pageable pageable);

    @Query("SELECT new com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HoaDonSanCaViewRequest" +
            "(hdsc.id, sc.id, hd.id, sb.tenSanBong, c.tenCa, hdsc.ngayDenSan, hdsc.thoiGianCheckIn, hd.tenNguoiDat, hd.soDienThoaiNguoiDat, hdsc.maQR, hdsc.tienSan, hdsc.trangThai) " +
            "FROM HoaDonSanCa hdsc " +
            "JOIN SanCa sc ON hdsc.idSanCa = sc.id " +
//            "JOIN DichVuSanBong dvsb ON hdsc.idDichVuSanBong = dvsb.id " +
            "JOIN HoaDon hd ON hdsc.idHoaDon = hd.id " +
            "JOIN SanBong sb ON sc.idSanBong = sb.id " +
            "JOIN Ca c ON sc.idCa = c.id " +
            "WHERE hdsc.trangThai = 1")
    Page<HoaDonSanCaViewRequest> findHoaDonSanCa(Pageable pageable);

    @Query("SELECT new com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HoaDonSanCaViewRequest" +
//            "(hdsc.id, sc.id, hd.id, dvsb.id, sb.tenSanBong, c.tenCa, hdsc.ngayDenSan, hdsc.thoiGianCheckIn, hd.tenNguoiDat, hd.soDienThoaiNguoiDat, hdsc.maQR, hdsc.tongTien, hdsc.trangThai) " +
            "(hdsc.id, sc.id, hd.id, sb.tenSanBong, c.tenCa, hdsc.ngayDenSan, hdsc.thoiGianCheckIn, hd.tenNguoiDat, hd.soDienThoaiNguoiDat, hdsc.maQR, hdsc.tienSan, hdsc.trangThai) " +
            "FROM HoaDonSanCa hdsc " +
            "JOIN SanCa sc ON hdsc.idSanCa = sc.id " +
//            "JOIN DichVuSanBong dvsb ON hdsc.idDichVuSanBong = dvsb.id " +
            "JOIN HoaDon hd ON hdsc.idHoaDon = hd.id " +
            "JOIN SanBong sb ON sc.idSanBong = sb.id " +
            "JOIN Ca c ON sc.idCa = c.id " +
            "WHERE hdsc.id = :id")
    HoaDonSanCaViewRequest findHoaDonSanCaById(@Param("id") String id);
}
