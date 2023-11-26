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
                   select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse(month(hdsc.ngayThanhToan) as thang, sum(hdsc.tongTienHoaDonSanCa)) 
                   from HoaDonSanCa hdsc 
                   where hdsc.trangThai = 3 and year(hdsc.ngayThanhToan) = :year
                   group by month(hdsc.ngayThanhToan)
                   order  by month(hdsc.ngayThanhToan) ASC
            """)
    List<ThongKeTheoNamAdminResponse> sumTongTienByMonthAndYear(@Param("year") Integer year);

    @Query(value = """
                select sum(hdsc.tong_tien_hoa_don_san_ca) as TongTien
                from hoa_don_san_ca hdsc
                where DAY(hdsc.ngay_thanh_toan)= :ngay
                and month(hdsc.ngay_thanh_toan)=:thang
                and YEAR(hdsc.ngay_thanh_toan) =:nam
                and time(hdsc.ngay_thanh_toan) between :timeStart and :timeEnd
            """, nativeQuery = true)
    Double thongKeTheoCaTrongNgay(@Param("ngay") Integer ngay, @Param("thang") Integer thang, @Param("nam") Integer nam, @Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd);

    // so luong do thue da dung theo nam
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongDoThue),
                    dt.tenDoThue,
                    dt.donGia,
                    (dt.donGia * sum(dvsb.soLuongDoThue))
                    )
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join DoThue dt on dvsb.idDoThue  = dt.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan) = :year
                    group by dvsb.idDoThue 
            """)
    List findThongKeDoThueTheoNam(@Param("year") Integer year);

    // so luong do thue da dung theo thang cua nam
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongDoThue),
                    dt.tenDoThue,
                    dt.donGia,
                    (dt.donGia * sum(dvsb.soLuongDoThue)))
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join DoThue dt on dvsb.idDoThue  = dt.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan ) = :year
                    and month(hdsc.ngayThanhToan) = :month
                    group by dvsb.idDoThue
            """)
    List findThongKeDoThueTheoThangTrongNam(@Param("year") Integer year, @Param("month") Integer month);

    // so luong do thue da dung theo ngay
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongDoThue),
                    dt.tenDoThue,
                    dt.donGia,
                    (dt.donGia * sum(dvsb.soLuongDoThue)))
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join DoThue dt on dvsb.idDoThue  = dt.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan ) = :year
                    and month(hdsc.ngayThanhToan) = :month
                    and day(hdsc.ngayThanhToan) = :day
                    group by dvsb.idDoThue
            """)
    List findThongKeDoThueTheoNgay(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);

    //nuoc uống
    // so luong do thue da dung theo nam
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongNuocUong),
                    nu.tenNuocUong,
                    nu.donGia,
                    (nu.donGia * sum(dvsb.soLuongNuocUong))
                    )
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join NuocUong nu on dvsb.idNuocUong  = nu.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan) = :year
                    group by dvsb.idNuocUong 
            """)
    List findThongKeNuocUongTheoNam(@Param("year") Integer year);

    // theo thang
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongNuocUong),
                    nu.tenNuocUong,
                    nu.donGia,
                    (nu.donGia * sum(dvsb.soLuongNuocUong))
                    )
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join NuocUong nu on dvsb.idNuocUong  = nu.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan) = :year
                    and month(hdsc.ngayThanhToan) = :month
                    group by dvsb.idNuocUong 
            """)
    List findThongKeNuocUongTheoThangTrongNam(@Param("year") Integer year, @Param("month") Integer month);

    // theo ngay
    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeLoaiDichVuAdminResponse(
                    sum(dvsb.soLuongNuocUong),
                    nu.tenNuocUong,
                    nu.donGia,
                    (nu.donGia * sum(dvsb.soLuongNuocUong))
                    )
                    from HoaDonSanCa hdsc
                    		join DichVuSanBong dvsb on hdsc .id  = dvsb.idHoaDonSanCa
                    		join NuocUong nu on dvsb.idNuocUong  = nu.id
                    where hdsc.trangThai  =3
                    and year(hdsc.ngayThanhToan ) = :year
                    and month(hdsc.ngayThanhToan) = :month
                    and day(hdsc.ngayThanhToan) = :day
                    group by dvsb.idNuocUong 
            """)
    List findThongKeNuocUongTheoNgay(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);
}
