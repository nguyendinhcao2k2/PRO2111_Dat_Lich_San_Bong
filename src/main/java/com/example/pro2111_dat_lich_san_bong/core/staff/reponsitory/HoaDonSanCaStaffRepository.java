package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonSanCaStaffReponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonSanCaStaffRepository extends JpaRepository<HoaDonSanCa, String> {
    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest" +
            "(HDSC.id, HD.tienCoc,HDSC.tienCocThua, HD.tenNguoiDat, HD.soDienThoaiNguoiDat, SB.tenSanBong, LS.tenLoaiSan, C.tenCa, C.thoiGianBatDau, C.thoiGianKetThuc, HDSC.ngayDenSan, HDSC.thoiGianCheckIn, HD.ngayTao, SC.gia, HDSC.trangThai) " +
            "FROM HoaDonSanCa HDSC " +
            "JOIN SanCa SC ON HDSC.idSanCa = SC.id " +
            "JOIN SanBong SB ON SC.idSanBong = SB.id " +
            "JOIN Ca C ON SC.idCa = C.id " +
            "JOIN LoaiSan LS ON SB.idLoaiSan = LS.id " +
            "JOIN HoaDon HD ON HD.id = HDSC.idHoaDon " +
            "WHERE HDSC.trangThai = :trangThaiDaCheckIn OR HDSC.trangThai = :trangThaiChoThanhToan")
    List<HoaDonThanhToanRequest> findAllByTrangThai(@Param("trangThaiDaCheckIn") int trangThaiDaCheckIn, @Param("trangThaiChoThanhToan") int trangThaiChoThanhToan);

    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest" +
            "(HDSC.id, HD.tienCoc, HDSC.tienCocThua,HD.tenNguoiDat, HD.soDienThoaiNguoiDat, SB.tenSanBong, LS.tenLoaiSan, C.tenCa, C.thoiGianBatDau, C.thoiGianKetThuc, HDSC.ngayDenSan, HDSC.thoiGianCheckIn, HD.ngayTao, SC.gia, HDSC.trangThai) " +
            "FROM HoaDonSanCa HDSC " +
            "JOIN SanCa SC ON HDSC.idSanCa = SC.id " +
            "JOIN SanBong SB ON SC.idSanBong = SB.id " +
            "JOIN Ca C ON SC.idCa = C.id " +
            "JOIN LoaiSan LS ON SB.idLoaiSan = LS.id " +
            "JOIN HoaDon HD ON HD.id = HDSC.idHoaDon " +
            "WHERE HDSC.id = :id")
    HoaDonThanhToanRequest findOneById(@Param("id") String id);

    @Query(value = "SELECT new com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest" +
            "(HDSC.id, HD.tienCoc, HDSC.tienCocThua,HD.tenNguoiDat, HD.soDienThoaiNguoiDat, SB.tenSanBong, LS.tenLoaiSan, C.tenCa, C.thoiGianBatDau, C.thoiGianKetThuc, HDSC.ngayDenSan, HDSC.thoiGianCheckIn, HD.ngayTao, SC.gia, HDSC.trangThai) " +
            "FROM HoaDonSanCa HDSC " +
            "JOIN SanCa SC ON HDSC.idSanCa = SC.id " +
            "JOIN SanBong SB ON SC.idSanBong = SB.id " +
            "JOIN Ca C ON SC.idCa = C.id " +
            "JOIN LoaiSan LS ON SB.idLoaiSan = LS.id " +
            "JOIN HoaDon HD ON HD.id = HDSC.idHoaDon " +
            "WHERE HD.soDienThoaiNguoiDat = :id AND (HDSC.trangThai = :trangThaiDaCheckIn OR HDSC.trangThai = :trangThaiChoThanhToan)")
    List<HoaDonThanhToanRequest> findAllBySoDienThoai(@Param("id") String keySearch, @Param("trangThaiDaCheckIn") int trangThaiDaCheckIn, @Param("trangThaiChoThanhToan") int trangThaiChoThanhToan);

    @Query(value = "SELECT hdsc.id_san_ca FROM hoa_don_san_ca hdsc where hdsc.id_hoa_don = ?1", nativeQuery = true)
    List<String> findIdSanCaByIdHoaDon(String idHoaDon);

    @Modifying
    @Query(value = "DELETE FROM hoa_don_san_ca hdsc WHERE hdsc.id_hoa_don = ?1", nativeQuery = true)
    void deleteByIdHoaDon(String idHoaDon);

    @Query(value = "SELECT hdsc.id FROM hoa_don_san_ca hdsc where hdsc.id_san_ca = ?1", nativeQuery = true)
    String findByIdSanCa(String idSanCa);


    @Query("""
                    select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonSanCaStaffReponse(
                        hdsc.id,hdsc.ngayDenSan,hdsc.thoiGianCheckIn,hdsc.tienSan,hdsc.tongTienHoaDonSanCa,hdsc.idHoaDon,hdsc.trangThai,hdsc.ngayThanhToan,
                        hdsc.countLich,hdsc.tienCocThua,httt.loaiHinhThanhToan
                    )
                     from HoaDonSanCa hdsc
                        left outer join HinhThucThanhToan httt on hdsc.id = httt.idHoaDonSanCa
                     where hdsc.idHoaDon = :idHoaDon 
            """)
    List<LichSuHoaDonSanCaStaffReponse> findAllLichSuHoaDonSanCaTheoIdHD(@Param("idHoaDon") String idHoaDon);
}
