package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import com.example.pro2111_dat_lich_san_bong.repository.ThoiGianDatLichRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    @Query(value = """
        select
            '1' as 'idSanBong',
              dl.gia_dat_lich as 'gia',
                99 as'trangThai',
               c.ten_ca as'tenCa',
               c.thoi_gian_bat_dau as 'thoiGianBatDau',
               c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
               dl.ngay_dat as 'thoiGianDat'
               from thoi_gian_dat_lich dl
                join ca c on dl.id_ca = c.id
               join hoa_don hd on dl.id_hoa_don = hd.id
                
               where hd.id_account =:#{#request.idAcount}
               and dl.id_loai_san = :#{#request.idLoaiSan} 
               and dl.trang_thai =:status
        order by c.ten_ca asc
    """,nativeQuery = true)
    List<SanCaUserResponse> getAllTGDLByUserId(@Param("request") EventRequest request, @Param("status")int trangThai);
}
