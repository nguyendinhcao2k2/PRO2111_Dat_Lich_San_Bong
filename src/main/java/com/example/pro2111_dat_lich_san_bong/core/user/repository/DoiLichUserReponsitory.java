package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoiLichUserReponsitory extends HoaDonSanCaReponsitory {

    HoaDonSanCa findFirstByIdSanCa(String idSanCa);

    @Query("""
                    select new com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse(
                       hdsc.id,hdsc.ngayDenSan,hdsc.tienSan,hdsc.idHoaDon,hdsc.idSanCa,hdsc.trangThai,c.id,c.tenCa,c.thoiGianBatDau,c.thoiGianKetThuc,c.giaCa,sb.id,sb.tenSanBong,ls.id,ls.tenLoaiSan,ls.giaSan,hdsc.countLich
                    )
                    from HoaDonSanCa hdsc
                        join SanCa sc on hdsc.idSanCa = sc.id
                        join SanBong sb on sc.idSanBong = sb.id
                        join LoaiSan ls on sb.idLoaiSan = ls.id
                        join Ca c on sc.idCa = c.id
                    where hdsc.trangThai = :trangThai and hdsc.idSanCa = :idSanCa
            """)
    DoiLichOneUserResponse findFirstByIdSanCaAndTrangThai(@Param("trangThai") Integer trangThaiHdsc, @Param("idSanCa") String idSanCa);


}
