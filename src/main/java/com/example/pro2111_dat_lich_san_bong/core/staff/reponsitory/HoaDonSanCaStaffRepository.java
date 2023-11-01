package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonSanCaStaffRepository extends JpaRepository<HoaDonSanCa, String> {
    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest" +
            "(HDSC.id, DVSB.id, A.id, HD.tienCoc,HD.tenNguoiDat, HD.soDienThoaiNguoiDat, SB.tenSanBong, LS.tenLoaiSan, C.tenCa, C.thoiGianBatDau, C.thoiGianKetThuc, HDSC.ngayDenSan, HDSC.thoiGianCheckIn, HD.ngayXacNhanDatSan, SC.gia, DVSB.donGia, SC.gia, HDSC.trangThai) " +
            "FROM HoaDonSanCa HDSC " +
            "JOIN SanCa SC ON HDSC.idSanCa = SC.id " +
            "JOIN SanBong SB ON SC.idSanBong = SB.id " +
            "JOIN Ca C ON SC.idCa = C.id " +
            "JOIN LoaiSan LS ON SB.idLoaiSan = LS.id " +
            "RIGHT JOIN DichVuSanBong DVSB ON DVSB.idHoaDonSanCa = HDSC.id " +
            "JOIN HoaDon HD ON HD.id = HDSC.idHoaDon " +
            "JOIN Account A ON HD.idAccount = A.id WHERE HDSC.trangThai = :trangThai")
    List<HoaDonThanhToanRequest> findAllByTrangThai(@Param("trangThai") int trangThai);

    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest" +
            "(HDSC.id, DVSB.id, A.id, HD.tienCoc,HD.tenNguoiDat, HD.soDienThoaiNguoiDat, SB.tenSanBong, LS.tenLoaiSan, C.tenCa, C.thoiGianBatDau, C.thoiGianKetThuc, HDSC.ngayDenSan, HDSC.thoiGianCheckIn, HD.ngayXacNhanDatSan, SC.gia, DVSB.donGia, SC.gia, HDSC.trangThai) " +
            "FROM HoaDonSanCa HDSC " +
            "JOIN SanCa SC ON HDSC.idSanCa = SC.id " +
            "JOIN SanBong SB ON SC.idSanBong = SB.id " +
            "JOIN Ca C ON SC.idCa = C.id " +
            "JOIN LoaiSan LS ON SB.idLoaiSan = LS.id " +
            "RIGHT JOIN DichVuSanBong DVSB ON DVSB.idHoaDonSanCa = HDSC.id " +
            "JOIN HoaDon HD ON HD.id = HDSC.idHoaDon " +
            "JOIN Account A ON HD.idAccount = A.id WHERE HDSC.id = :id")
    HoaDonThanhToanRequest findOneById(@Param("id") String id);

    @Query(value = "SELECT hdsc.id_san_ca FROM hoa_don_san_ca hdsc where hdsc.id_hoa_don = ?1", nativeQuery = true)
    List<String> findIdSanCaByIdHoaDon(String idHoaDon);

    @Modifying
    @Query(value = "DELETE FROM hoa_don_san_ca hdsc WHERE hdsc.id_hoa_don = ?1", nativeQuery = true)
    void deleteByIdHoaDon(String idHoaDon);
}
