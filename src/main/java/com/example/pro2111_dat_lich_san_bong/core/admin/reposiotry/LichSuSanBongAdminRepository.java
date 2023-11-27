package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.repository.LichSuSanBongRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public interface LichSuSanBongAdminRepository extends LichSuSanBongRepository {

    // so luot da,huy,doi san trong ngay
    @Query(value = """
                select  count(lssb.id) 
                	from lich_su_san_bong lssb
                	where lssb.trang_thai  = :trangThaiLichSu and date(lssb.ngay_thuc_hien) = date(:ngayThucHien)
                	group by date(lssb.ngay_thuc_hien)
            """, nativeQuery = true)
    Integer thongKeSoLuot(@Param("trangThaiLichSu") Integer trangThaiLichSu, @Param("ngayThucHien") LocalDate ngayThucHien);

    //theo ngay

    //theo tuần
    @Query(value = """
                select  count(lssb.id) 
                	from lich_su_san_bong lssb
                	where lssb.trang_thai  = :trangThaiLichSu and yearweek(lssb.ngay_thuc_hien,1) = :yearWeek
                	group by yearweek(lssb.ngay_thuc_hien,1)
            """, nativeQuery = true)
    Integer thongKeSoLuotTheoTuan(@Param("trangThaiLichSu") Integer trangThaiLichSu, @Param("yearWeek") Integer yearWeek);
    //theo tuần

    //theo thang
    @Query(value = """
                select  count(lssb.id) 
                	from lich_su_san_bong lssb
                	where lssb.trang_thai  = :trangThaiLichSu and month(lssb.ngay_thuc_hien) = month(:ngayThucHien)
                	group by month(lssb.ngay_thuc_hien)
            """, nativeQuery = true)
    Integer thongKeSoLuotTheoThang(@Param("trangThaiLichSu") Integer trangThaiLichSu, @Param("ngayThucHien") LocalDate ngayThucHien);

    //theo thang
    //theo nam
    @Query(value = """
                select  count(lssb.id) 
                	from lich_su_san_bong lssb
                	where lssb.trang_thai  = :trangThaiLichSu and year(lssb.ngay_thuc_hien) = :namThucHien
                	group by year(lssb.ngay_thuc_hien)
            """, nativeQuery = true)
    Integer thongKeSoLuotTheoNam(@Param("trangThaiLichSu") Integer trangThaiLichSu, @Param("namThucHien") Integer namThucHien);
    //theo nam

}
