package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public interface HoaDonThongKeAdminReponsitory extends HoaDonRepository {
    // so luot dat san online theo ngay
    @Query(value = """
             select  count(hdsc.id) as soLichOnline from hoa_don hd
             		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is not null and date(hd.ngay_tao) = date(:ngayTao)
            """, nativeQuery = true)
    Integer soLuotDatOnlineTheoNgay(@Param("ngayTao") LocalDate ngayTao);

    // so luot dat san nho theo ngay
    @Query(value = """
              select  count(hdsc.id) as soLichOnline from hoa_don hd
              		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is null and date(hd.ngay_tao) = date(:ngayTao)
            """, nativeQuery = true)
    Integer soLuotDatNhoTheoNgay(@Param("ngayTao") LocalDate ngayTao);

    //theo tuan
    @Query(value = """
             select  count(hdsc.id) as soLichOnline from hoa_don hd
             		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is not null and yearweek(hd.ngay_tao,1) = :yearWeek
            """, nativeQuery = true)
    Integer soLuotDatOnlineTheoTuan(@Param("yearWeek") Integer yearWeek);


    @Query(value = """
              select  count(hdsc.id) as soLichOnline from hoa_don hd
              		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is null and yearWeek(hd.ngay_tao,1) = :yearWeek
            """, nativeQuery = true)
    Integer soLuotDatNhoTheoTuan(@Param("yearWeek") Integer yearWeek);
    //theo tuan

    //theo thang
    @Query(value = """
             select  count(hdsc.id) as soLichOnline from hoa_don hd
             		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is not null and month(hd.ngay_tao) = month(:ngayTao)
            """, nativeQuery = true)
    Integer soLuotDatOnlineTheoThang(@Param("ngayTao") LocalDate ngayTao);


    @Query(value = """
              select  count(hdsc.id) as soLichOnline from hoa_don hd
              		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is null and month(hd.ngay_tao) = month(:ngayTao)
            """, nativeQuery = true)
    Integer soLuotDatNhoTheoThang(@Param("ngayTao") LocalDate ngayTao);
    //theo thang

    //theo nam
    @Query(value = """
             select  count(hdsc.id) as soLichOnline from hoa_don hd
             		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is not null and year(hd.ngay_tao) = :namTao
            """, nativeQuery = true)
    Integer soLuotDatOnlineTheoNam(@Param("namTao") Integer namTao);


    @Query(value = """
              select  count(hdsc.id) as soLichOnline from hoa_don hd
              		join hoa_don_san_ca hdsc on hd.id  = hdsc.id_hoa_don
             		where hd.id_account is null and year(hd.ngay_tao) = :namTao
            """, nativeQuery = true)
    Integer soLuotDatNhoTheoNam(@Param("namTao") Integer namTao);
    //theo nam


}
