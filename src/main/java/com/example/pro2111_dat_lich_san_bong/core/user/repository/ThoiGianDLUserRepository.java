package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import com.example.pro2111_dat_lich_san_bong.repository.ThoiGianDatLichRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author thepvph20110
 */
public interface ThoiGianDLUserRepository extends ThoiGianDatLichRepository {
    @Query("""
                    select tgdl
                    from Ca c 
                        join ThoiGianDatLich tgdl on c.id = tgdl.idCa
                        join HoaDon  hd on tgdl.idHoaDon = hd.id
                        join LoaiSan ls on tgdl.idLoaiSan = ls.id
                        where hd.id = :idHD 
                            and DATE(tgdl.ngayDat) = :ngayDat 
                            and c.id = :idCA 
                            and ls.id = :idLS 
                            and hd.trangThai = 0
            """)
    ThoiGianDatLich findTGDLByIdCaAndIdLsAndNgayDa(@Param(("idHD")) String idHD, @Param(("ngayDat")) Date ngayDat, @Param(("idCA")) String idCA, @Param(("idLS")) String idLS);

}
