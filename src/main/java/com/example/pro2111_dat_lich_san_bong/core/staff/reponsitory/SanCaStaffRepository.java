package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.EventStaffRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanCaStaffEventResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanCaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caodinh
 */
public interface SanCaStaffRepository extends JpaRepository<SanCa, String> {

    @Query(value = "SELECT sc.id as idSanCa,c.thoi_gian_bat_dau as thoiGianBatDau,sb.id as idSanBong,sc.trang_thai as trangThai FROM san_ca as sc inner join san_bong as sb" +
            " on sc.id_san_bong = sb.id inner join ca as c on c.id = sc.id_ca where sc.ngay_den_san " +
            " = ?1", nativeQuery = true)
    List<SanCaStaffResponse> findAllByDate(LocalDate localDate);

    @Query(value = "SELECT sc.id as idSanCa,c.thoi_gian_bat_dau as thoiGianBatDau,sb.id as idSanBong,sc.trang_thai as trangThai FROM san_ca as sc inner join san_bong as sb" +
            " on sc.id_san_bong = sb.id inner join ca as c on c.id = sc.id_ca ", nativeQuery = true)
    List<SanCaStaffResponse> findAllSanCa();

    @Query(value = """
        select sc.id, sc.id_san_bong as 'idSanBong',
        sc.gia,sc.trang_thai as'trangThai',
        c.ten_ca as'tenCa', c.thoi_gian_bat_dau as 'thoiGianBatDau',
        c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
        sc.ngay_den_san as 'thoiGianDat',
        hd_sc.dem_so_lan_doi_lich as 'soLanDoiLich',
        hd.ten_nguoi_dat  as 'tenNguoiDat'
        from san_ca sc 
        join hoa_don_san_ca hd_sc on hd_sc.id_san_ca = sc.id
        join ca c on sc.id_ca = c.id
        join hoa_don hd on hd.id = hd_sc.id_hoa_don
        join san_bong sb on sb.id = sc.id_san_bong
        where 
        sb.id_loai_san = :#{#request.idLoaiSan}
        and sc.ngay_den_san between :#{#request.ngayBatDau} and :#{#request.ngayKetThuc}
        order by c.ten_ca asc
    """,nativeQuery = true)
    List<SanCaStaffEventResponse> getAllSanCa(@Param("request") EventStaffRequest request);

}
