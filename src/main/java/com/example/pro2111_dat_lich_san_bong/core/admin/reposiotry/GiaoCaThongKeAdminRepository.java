package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public interface GiaoCaThongKeAdminRepository extends GiaoCaRepository {

    @Query(value = """
            select sum(gc.tien_phat_sinh)  from giao_ca gc\s
            		where gc.tien_phat_sinh is not null
            				and gc.trang_thai  = 1
            				and date(gc.thoi_gian_ket_ca) = date(:thoiGianKetCa)
            		group by date(gc.thoi_gian_ket_ca)
            """, nativeQuery = true)
    Double tongTienPhatSinhCuaCacCaTheoNgay(@Param("thoiGianKetCa") LocalDate thoiGianKetCa);
}
