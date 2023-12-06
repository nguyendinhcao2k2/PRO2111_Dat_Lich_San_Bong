package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocNamReponse;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViTienCocAdminRepository extends ViTienRepository {
        @Query(value = """
            select sum(vt.so_tien) from vi_tien vt\s
            		where vt.type_payment  = :hinhThucThanhToan
            		    and date(vt.thoi_gian_tao) = :ngayTao
            		group by date(vt.thoi_gian_tao)
""",nativeQuery = true)
     Double tongTienThanhToanCocTheoHinhThucThanhToanNgay(@Param("hinhThucThanhToan") Integer hinhThucThanhToan,@Param("ngayTao") LocalDate ngayTao);

    @Query(value = """
            select sum(vt.so_tien) from vi_tien vt\s
            		where vt.type_payment  = :hinhThucThanhToan
            		    and yearweek(vt.thoi_gian_tao,1) = :tuanTao
            		group by yearweek(vt.thoi_gian_tao,1)
""",nativeQuery = true)
    Double tongTienThanhToanCocTheoHinhThucThanhToanTuan(@Param("hinhThucThanhToan") Integer hinhThucThanhToan,@Param("tuanTao") Integer tuanTao);

    @Query(value = """
             select sum(vt.so_tien) from vi_tien vt\s
            		where vt.type_payment  = :hinhThucThanhToan
            		    and month(vt.thoi_gian_tao) = :thangTao
            		    and year(vt.thoi_gian_tao) = :namTao
            		group by month(vt.thoi_gian_tao)
""",nativeQuery = true)
    Double tongTienThanhToanCocTheoHinhThucThanhToanThang(@Param("hinhThucThanhToan") Integer hinhThucThanhToan,@Param("thangTao") Integer thangTao, @Param("namTao") Integer namTao);

    @Query(value = """
            select sum(vt.so_tien) from vi_tien vt\s
            		where vt.type_payment  = :hinhThucThanhToan
            		    and year(vt.thoi_gian_tao) = :namTao
            		group by year(vt.thoi_gian_tao)
""",nativeQuery = true)
    Double tongTienThanhToanCocTheoHinhThucThanhToanNam(@Param("hinhThucThanhToan") Integer hinhThucThanhToan,@Param("namTao") Integer namTao);


    @Query(value = """
            select sum(vt.so_tien) from vi_tien vt\s
            		where vt.type_payment  = :hinhThucThanhToan
            		    and vt.thoi_gian_tao between :thoiGianNhanCa and now()
            		group by date(vt.thoi_gian_tao)
""",nativeQuery = true)
    Double tongTienThanhToanCocTheoHinhThucThanhToanTrongCaLamViec(@Param("hinhThucThanhToan") Integer hinhThucThanhToan,@Param("thoiGianNhanCa") Timestamp thoiGianNhanCa);

    @Query(value = """
            select sum(vt.so_tien) from vi_tien vt
            		where day(vt.thoi_gian_tao) = :ngay 
            		    and month(vt.thoi_gian_tao) = :thang
            		    and year(vt.thoi_gian_tao) = :nam
            		    and time(vt.thoi_gian_tao) between :timeStart and :timeEnd
""",nativeQuery = true)
    Double tongTienThanhToanCocTrongNgayTheoCa(@Param("ngay") Integer ngay, @Param("thang") Integer thang, @Param("nam") Integer nam, @Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd);

    @Query("""
        select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocNamReponse(month(vt.thoiGianTao),sum(vt.soTien))
        from ViTienCoc vt
            where year(vt.thoiGianTao) = :year
            group by month(vt.thoiGianTao)
            order  by month(vt.thoiGianTao) ASC
            
""")
    List<ViTienCocNamReponse> findListTienCocNam(@Param("year") Integer year);
}
