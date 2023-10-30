package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author caodinh
 */
public interface HoaDonStaffRepository extends JpaRepository<HoaDon, String> {

    @Query(nativeQuery = true,value = "SELECT " +
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
}
