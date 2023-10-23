package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
            select sum(hdsc.tong_tien)  as TongTien  from  giao_ca gc
            	join hoa_don_san_ca hdsc  on gc.id  = hdsc.id_giao_ca
            	join hinh_thuc_thanh_toan httt on hdsc.id = httt.id_hoa_don_san_ca
            	where  gc.trang_thai = 0
            	and  hdsc.ngay_thanh_toan  between gc.thoi_gian_nhan_ca and now()
            	and hdsc.trang_thai =0
            	and httt.hinh_thuc_thanh_toan = :hinhTT
            	group by gc.id
            """, nativeQuery = true)
    Double tongTienTrongCaTheoHinhThucThanhToan(@Param("hinhTT") Integer loaiHinhThanhToan);

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse
                    (
                           gc.id,
                            acTrongCa.displayName,
                            acTiepTheo.displayName,
                            gc.thoiGianNhanCa,
                            gc.thoiGianKetCa,
                            gc.thoiGianReset,
                            gc.tongTienTrongCa,
                            gc.tienBanDau,
                            gc.tongTienMatRut,
                            gc.tienPhatSinh,
                            gc.ghiChuPhatSinh,
                            gc.tongTienMat,
                            gc.tongTienKhac
                    ) 
                    FROM  GiaoCa gc
                            LEFT JOIN Account acTrongCa ON gc.idNhanVienTrongCa = acTrongCa.id
                            LEFT JOIN Account acTiepTheo ON gc.idNhanVienCaTiepTheo = acTiepTheo.id
                            where gc.trangThai =:status
            """)
    Page<QuanLyGiaoCaResponse> findAllGiaoCaByStatus(Pageable pageable, @Param("status") TrangThaiGiaoCa statuss);

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse
                    (
                           gc.id,
                            acTrongCa.displayName,
                            acTiepTheo.displayName,
                            gc.thoiGianNhanCa,
                            gc.thoiGianKetCa,
                            gc.thoiGianReset,
                            gc.tongTienTrongCa,
                            gc.tienBanDau,
                            gc.tongTienMatRut,
                            gc.tienPhatSinh,
                            gc.ghiChuPhatSinh,
                             gc.tongTienMat,
                            gc.tongTienKhac
                    ) 
                    FROM  GiaoCa gc
                            LEFT JOIN Account acTrongCa ON gc.idNhanVienTrongCa = acTrongCa.id
                            LEFT JOIN Account acTiepTheo ON gc.idNhanVienCaTiepTheo = acTiepTheo.id
                            where gc.trangThai =1 AND acTrongCa.displayName like %:name%
            """)
    Page<QuanLyGiaoCaResponse> searchByName(Pageable pageable, @Param("name") String name);

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse
                    (
                           gc.id,
                            acTrongCa.displayName,
                            acTiepTheo.displayName,
                            gc.thoiGianNhanCa,
                            gc.thoiGianKetCa,
                            gc.thoiGianReset,
                            gc.tongTienTrongCa,
                            gc.tienBanDau,
                            gc.tongTienMatRut,
                            gc.tienPhatSinh,
                            gc.ghiChuPhatSinh,
                             gc.tongTienMat,
                            gc.tongTienKhac
                    ) 
                    FROM  GiaoCa gc
                            LEFT JOIN Account acTrongCa ON gc.idNhanVienTrongCa = acTrongCa.id
                            LEFT JOIN Account acTiepTheo ON gc.idNhanVienCaTiepTheo = acTiepTheo.id
                            where gc.trangThai =1 AND gc.tongTienMatRut >0
            """)
    Page<QuanLyGiaoCaResponse> giaoCaCoTienRut(Pageable pageable);

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse
                    (
                           gc.id,
                            acTrongCa.displayName,
                            acTiepTheo.displayName,
                            gc.thoiGianNhanCa,
                            gc.thoiGianKetCa,
                            gc.thoiGianReset,
                            gc.tongTienTrongCa,
                            gc.tienBanDau,
                            gc.tongTienMatRut,
                            gc.tienPhatSinh,
                            gc.ghiChuPhatSinh,
                             gc.tongTienMat,
                            gc.tongTienKhac
                    ) 
                    FROM  GiaoCa gc
                            LEFT JOIN Account acTrongCa ON gc.idNhanVienTrongCa = acTrongCa.id
                            LEFT JOIN Account acTiepTheo ON gc.idNhanVienCaTiepTheo = acTiepTheo.id
                            where gc.trangThai =1 AND DATE(gc.thoiGianNhanCa) = :timeNC
            """)
    Page<QuanLyGiaoCaResponse> giaoCaByThoiGianNhanCa(Pageable pageable, @Param("timeNC") Date time);
}
