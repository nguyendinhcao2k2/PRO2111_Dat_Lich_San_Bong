package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.DoiLichNhieuStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                          order by hdsc.ngayDenSan asc
                        	
            """)
    List<DoiLichNhieuUserResponse> findListLichDoi(@Param("timeChoPhepDoiLich") Integer timeChoPhepDoiLich,@Param("idNguoiDat")String idNguoiDat);

    @Query(value = """
                        select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.DoiLichNhieuStaffReponse(
                        hdsc.id,hdsc.ngayDenSan,hdsc.tienSan,hdsc.idHoaDon,hdsc.idSanCa,hdsc.trangThai,c.id,c.tenCa,c.thoiGianBatDau,c.thoiGianKetThuc,
                                c.giaCa,sb.id,sb.tenSanBong,ls.id,ls.tenLoaiSan,ls.giaSan,hdsc.countLich,hd.tenNguoiDat,hd.soDienThoaiNguoiDat,hd.ngayXacNhanDatSan)
                        	from HoaDon hd 
                        	join HoaDonSanCa hdsc on hd.id  = hdsc.idHoaDon
                        	join SanCa sc on hdsc.idSanCa  = sc .id
                        	join SanBong sb on sc.idSanBong  = sb.id
                        	join LoaiSan ls on sb.idLoaiSan  = ls.id
                        	join Ca c on sc.idCa  = c.id
                        where hdsc.countLich = 0
                        		and date(hdsc.ngayDenSan)  != curdate()
                        		and datediff(hdsc.ngayDenSan, curdate()) >= :timeChoPhepDoiLich
                        		and hd.idAccount is null
                        		and hdsc.trangThai = 0
                          order by hdsc.ngayDenSan asc
                        	
            """)
    Page<DoiLichNhieuStaffReponse> findListLichDoiDatHo(@Param("timeChoPhepDoiLich") Integer timeChoPhepDoiLich, Pageable pageable);

    @Query(value = """
                        select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.DoiLichNhieuStaffReponse(
                        hdsc.id,hdsc.ngayDenSan,hdsc.tienSan,hdsc.idHoaDon,hdsc.idSanCa,hdsc.trangThai,c.id,c.tenCa,c.thoiGianBatDau,c.thoiGianKetThuc,
                                c.giaCa,sb.id,sb.tenSanBong,ls.id,ls.tenLoaiSan,ls.giaSan,hdsc.countLich,hd.tenNguoiDat,hd.soDienThoaiNguoiDat,hd.ngayXacNhanDatSan)
                        	from HoaDon hd 
                        	join HoaDonSanCa hdsc on hd.id  = hdsc.idHoaDon
                        	join SanCa sc on hdsc.idSanCa  = sc .id
                        	join SanBong sb on sc.idSanBong  = sb.id
                        	join LoaiSan ls on sb.idLoaiSan  = ls.id
                        	join Ca c on sc.idCa  = c.id
                        where hdsc.countLich = 0
                        		and date(hdsc.ngayDenSan)  != curdate()
                        		and datediff(hdsc.ngayDenSan, curdate()) >= :timeChoPhepDoiLich
                        		and hd.idAccount is null
                        		and hd.soDienThoaiNguoiDat LIKE LOWER(CONCAT('%',:soDienThoaiNguoiDat,'%'))
                        		and hdsc.trangThai = 0
                        order by hdsc.ngayDenSan asc
                        	
            """)
    Page<DoiLichNhieuStaffReponse> tinKiemLichDatHoTheoSDT(@Param("timeChoPhepDoiLich") Integer timeChoPhepDoiLich, @Param("soDienThoaiNguoiDat") String soDienThoaiNguoiDat, Pageable pageable);
}
