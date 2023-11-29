package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GiaoCaStaffReponsitory extends GiaoCaRepository {

    GiaoCa findGiaoCaByTrangThai(TrangThaiGiaoCa trangThaiGiaoCa);

    GiaoCa findFirstByOrderByThoiGianNhanCaDesc();

    GiaoCa findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount);

    @Query(value = """
                    select count(hdsc.id)  as TONGSOHD  from  giao_ca gc
                                   	join hoa_don_san_ca hdsc  on gc.id  = hdsc.id_giao_ca
                                   	where  gc.trang_thai = :ttGC
                                   	and  hdsc.ngay_thanh_toan  between gc.thoi_gian_nhan_ca and now()
                                   	and hdsc.trang_thai = :ttHDSC
                                   	group by gc.id
            """, nativeQuery = true)
    Integer tongSoHoaDonTrongCaFollowStatus(@Param("ttGC") Integer trangThaiGC, @Param("ttHDSC") Integer trangThaiHDSC);

    @Query(value = """
            select sum(hdsc.tong_tien_hoa_don_san_ca)  as TongTien  from  giao_ca gc
            	join hoa_don_san_ca hdsc  on gc.id  = hdsc.id_giao_ca
            	join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca
            	where  gc.trang_thai = 0
            	and  hdsc.ngay_thanh_toan  between gc.thoi_gian_nhan_ca and now()
            	and hdsc.trang_thai =3
            	and httt.hinh_thuc_thanh_toan = :hinhTT
            	group by gc.id
            """, nativeQuery = true)
    Double tongTienTrongCaTheoHinhThucThanhToan(@Param("hinhTT") Integer loaiHinhThanhToan);


}
