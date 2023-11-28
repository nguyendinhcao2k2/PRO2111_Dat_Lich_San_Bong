package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public interface HoaDonSanCaThongKeAdminRepository extends HoaDonSanCaReponsitory {
    //theo ngay
    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and date(hdsc.ngay_thanh_toan) = date(:ngayThanhToan)
             		group by date(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienTheoNgay(@Param("ngayThanhToan") LocalDate ngayThanhToan);

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and date(hdsc.ngay_thanh_toan) = date(:ngayThanhToan)
             			and httt.hinh_thuc_thanh_toan  = :trangThai
             		group by date(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienMatTheoNgayVaHinhThucThanhToan(@Param("ngayThanhToan") LocalDate ngayThanhToan, @Param("trangThai") Integer loaiHinhThanhToan);

    //theo ngay

    //theo tuan

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and yearweek(hdsc.ngay_thanh_toan,1) = :yearWeek
             		group by yearweek(hdsc.ngay_thanh_toan,1)
            """, nativeQuery = true)
    Double tongTienTheoTuan(@Param("yearWeek") Integer yearWeek);

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and yearweek(hdsc.ngay_thanh_toan,1) = :yearWeek
             			and httt.hinh_thuc_thanh_toan  = :trangThai
             		group by yearweek(hdsc.ngay_thanh_toan,1)
            """, nativeQuery = true)
    Double tongTienMatTheoTuanVaHinhThucThanhToan(@Param("yearWeek") Integer yearWeek, @Param("trangThai") Integer loaiHinhThanhToan);

    //theo tuan

    //theo thang

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and month(hdsc.ngay_thanh_toan) = month(:ngayThanhToan)
             		group by month(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienTheoThang(@Param("ngayThanhToan") LocalDate ngayThanhToan);

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and month(hdsc.ngay_thanh_toan) = :thangThanhToan
             			and year(hdsc.ngay_thanh_toan) = :namThanhToan
             			and httt.hinh_thuc_thanh_toan  = :trangThai
             		group by month(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienMatTheoThangVaHinhThucThanhToan(@Param("thangThanhToan") Integer thangThanhToan, @Param("namThanhToan") Integer namThanhToan, @Param("trangThai") Integer loaiHinhThanhToan);

    //theo thang

    //theo nam

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and year(hdsc.ngay_thanh_toan) = :namThanhToan
             		group by year(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienTheoNam(@Param("namThanhToan") Integer namThanhToan);

    @Query(value = """
             select sum(hdsc.tong_tien_hoa_don_san_ca) 
             from hoa_don_san_ca hdsc\s
             		join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca\s
             		where hdsc.ngay_thanh_toan  is not null\s
             			and year(hdsc.ngay_thanh_toan) = :namThanhToan
             			and httt.hinh_thuc_thanh_toan  = :trangThai
             		group by year(hdsc.ngay_thanh_toan)
            """, nativeQuery = true)
    Double tongTienMatTheoNamVaHinhThucThanhToan(@Param("namThanhToan") Integer namThanhToan, @Param("trangThai") Integer loaiHinhThanhToan);


    //theo nam
}
