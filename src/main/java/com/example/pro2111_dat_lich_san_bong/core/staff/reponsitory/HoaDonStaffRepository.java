package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CheckInResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caodinh
 */
public interface HoaDonStaffRepository extends JpaRepository<HoaDon, String> {

    @Query(nativeQuery = true, value = "SELECT " +
            "    row_number() OVER() as Stt, " +
            "    hd.id as idHoaDon," +
            "    hd.ten_nguoi_dat as tenNguoiDat ," +
            "    hd.so_dien_thoai_nguoi_dat as soDienThoaiNguoiDat, " +
            "    hd.ngay_tao as ngay, " +
            "    hd.tong_tien as tongTien, " +
            "    hd.tien_coc as tienCoc," +
            "    hd.ma_tien_coc as maTienCoc, " +
            "    hd.email as email " +
            "FROM hoa_don as hd " +
            "WHERE hd.trang_thai = 0")
    List<HoaDonStaffResponse> getHoaDonByTrangThai();

    @Query(nativeQuery = true, value = "SELECT " +
            "    row_number() OVER() as Stt, " +
            "    hd.id as idHoaDon," +
            "    hd.ten_nguoi_dat as tenNguoiDat ," +
            "    hd.so_dien_thoai_nguoi_dat as soDienThoaiNguoiDat, " +
            "    hd.ngay_tao as ngay, " +
            "    hd.tong_tien as tongTien, " +
            "    hd.tien_coc as tienCoc," +
            "    hd.ma_tien_coc as maTienCoc, " +
            "    hd.email as email " +
            "FROM hoa_don as hd " +
            "WHERE hd.so_dien_thoai_nguoi_dat LIKE  ?1 " +
            "   OR hd.ten_nguoi_dat LIKE  ?1 " +
            "   AND hd.trang_thai = 0")
    List<HoaDonStaffResponse> filterHoaDon(String stringText);


    @Query(value = "SELECT " +
            "        row_number() OVER() as Stt, " +
            "        tb1.id as idHoaDon, " +
            "        tb2.id as idHDSanCa, " +
            "        tb3.id as idSanCa, " +
            "        tb4.id as idCa, " +
            "        tb5.id as idSanBong, " +
            "        tb1.ten_nguoi_dat as hoTen, " +
            "        tb1.so_dien_thoai_nguoi_dat as soDienThoai, " +
            "        tb5.ten_san_bong as tenSanBong, " +
            "        tb4.ten_ca as tenCa, " +
            "        tb4.thoi_gian_bat_dau as thoiGianBatDau, " +
            "        tb4.thoi_gian_ket_thuc as thoiGianKetThuc, " +
            "        tb2.ngay_den_san as ngayDenSan, " +
            "        tb1.tien_coc as tienCoc, " +
            "        tb2.tien_san as tienSan  " +
            "    FROM " +
            "        dat_lich_san_bong.hoa_don tb1 " +
            "    inner join " +
            "        dat_lich_san_bong.hoa_don_san_ca tb2 " +
            "            on tb1.id = tb2.id_hoa_don " +
            "    inner join " +
            "        dat_lich_san_bong.san_ca tb3 " +
            "            on tb2.id_san_ca = tb3.id " +
            "    inner join " +
            "        dat_lich_san_bong.ca tb4 " +
            "            on tb3.id_ca = tb4.id " +
            "    inner join " +
            "        dat_lich_san_bong.san_bong tb5 " +
            "            on tb5.id = tb3.id_san_bong " +
            "    where " +
            "        so_dien_thoai_nguoi_dat LIKE ?1 " +
            "        or tb1.ten_nguoi_dat LIKE ?1 " +
            "        and tb3.trang_thai = 0 " +
            "        and tb2.trang_thai = 0 ", nativeQuery = true)
    List<CheckInResponse> listCheckIn(String param);

    HoaDon findHoaDonById(String idHoaDon);

    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse(
                       hd.id,hd.ngayTao,hd.soDienThoaiNguoiDat,hd.tienCoc,hd.tongTien,hd.email,hd.tenNguoiDat,hd.trangThai
                    )  
                     from HoaDon hd 
                     where hd.idAccount  = :idAccount
            """)
    Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCaUser(Pageable pageable,@Param("idAccount") String idAccount);


    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse(
                       hd.id,hd.ngayTao,hd.soDienThoaiNguoiDat,hd.tienCoc,hd.tongTien,hd.email,hd.tenNguoiDat,hd.trangThai
                    )  
                     from HoaDon hd 
                     where hd.idAccount is null
            """)
    Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCa(Pageable pageable);

    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse(
                       hd.id,hd.ngayTao,hd.soDienThoaiNguoiDat,hd.tienCoc,hd.tongTien,hd.email,hd.tenNguoiDat,hd.trangThai
                    )  
                     from HoaDon hd 
                     where hd.idAccount is null
                        and (
                        (hd.tenNguoiDat is not null and hd.tenNguoiDat  LIKE LOWER(CONCAT('%',:ten,'%') ))
                        or
                        (hd.soDienThoaiNguoiDat is not null and hd.soDienThoaiNguoiDat LIKE LOWER(CONCAT('%',:soDienThoaiNguoiDat,'%') ))
                        ) 
                         
            """)
    Page<LichSuHoaDonStaffReponse> searchLichSuHoaDon(Pageable pageable, @Param("ten") String ten,@Param("soDienThoaiNguoiDat") String soDienThoaiNguoiDat);

    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse(
                       hd.id,hd.ngayTao,hd.soDienThoaiNguoiDat,hd.tienCoc,hd.tongTien,hd.email,hd.tenNguoiDat,hd.trangThai
                    )  
                     from HoaDon hd 
                     where hd.idAccount = :idAccount
                        and (
                        (hd.tenNguoiDat is not null and hd.tenNguoiDat  LIKE LOWER(CONCAT('%',:ten,'%') ))
                        or
                        (hd.soDienThoaiNguoiDat is not null and hd.soDienThoaiNguoiDat LIKE LOWER(CONCAT('%',:soDienThoaiNguoiDat,'%') ))
                        ) 
                         
            """)
    Page<LichSuHoaDonStaffReponse> searchLichSuHoaDonUser(Pageable pageable,@Param("idAccount")String idAccount, @Param("ten") String ten,@Param("soDienThoaiNguoiDat") String soDienThoaiNguoiDat);
}
