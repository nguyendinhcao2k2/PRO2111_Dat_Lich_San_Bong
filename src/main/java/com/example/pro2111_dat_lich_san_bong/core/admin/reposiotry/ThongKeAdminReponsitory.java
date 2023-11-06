package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongKeAdminReponsitory extends HoaDonSanCaReponsitory {
    @Query(value = """
                   select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse(month(hdsc.ngayThanhToan) as thang, sum(hdsc.tienSan)) 
                   from HoaDonSanCa hdsc 
                   where hdsc.trangThai = 0 and year(hdsc.ngayThanhToan) = :year
                   group by month(hdsc.ngayThanhToan)
                   order  by month(hdsc.ngayThanhToan) ASC
            """)
    List<ThongKeTheoNamAdminResponse> sumTongTienByMonthAndYear(@Param("year") Integer year);

    @Query(value = """
                select sum(hdsc.tien_san) as TongTien
                from hoa_don_san_ca hdsc
                where DAY(hdsc.ngay_thanh_toan)= :ngay
                and month(hdsc.ngay_thanh_toan)=:thang
                and YEAR(hdsc.ngay_thanh_toan) =:nam
                and time(hdsc.ngay_thanh_toan) between :timeStart and :timeEnd
            """, nativeQuery = true)
    Double thongKeTheoCaTrongNgay(@Param("ngay") Integer ngay, @Param("thang") Integer thang, @Param("nam") Integer nam, @Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd);
}
