package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoiLichNhieuUserReponsitory extends HoaDonSanCaReponsitory {

    @Query(value = """
                        select  new com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse(
                        hdsc.id,hdsc.ngayDenSan,hdsc.tienSan,hdsc.idHoaDon,hdsc.idSanCa,hdsc.trangThai,c.id,c.tenCa,c.thoiGianBatDau,c.thoiGianKetThuc,
                                c.giaCa,sb.id,sb.tenSanBong,ls.id,ls.tenLoaiSan,ls.giaSan,hdsc.countLich)
                        	from HoaDon hd 
                        	join HoaDonSanCa hdsc on hd.id  = hdsc.idHoaDon
                        	join SanCa sc on hdsc.idSanCa  = sc .id
                        	join SanBong sb on sc.idSanBong  = sb.id
                        	join LoaiSan ls on sb.idLoaiSan  = ls.id
                        	join Ca c on sc.idCa  = c.id
                        where hdsc.countLich = 0
                        		and date(hdsc.ngayDenSan)  != curdate()
                        		and datediff(hdsc.ngayDenSan, curdate()) >= :timeChoPhepDoiLich
                        		and hd.idAccount = :idNguoiDat
                        		and hdsc.trangThai = 0
                        	
            """)
    List<DoiLichNhieuUserResponse> findListLichDoi(@Param("timeChoPhepDoiLich") Integer timeChoPhepDoiLich,@Param("idNguoiDat")String idNguoiDat);
}
