package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GiaoCaAdminRepository extends GiaoCaRepository {
    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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
                            where gc.trangThai =:status and  gc.id=:id
            """)
    QuanLyGiaoCaResponse findGiaoCaByIdAndStatus(@Param("status") TrangThaiGiaoCa statuss, @Param("id") String id);

    @Query(value = """
                    select  new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse
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
                            where gc.trangThai =1 
                            ORDER BY gc.thoiGianNhanCa DESC 
            """)
    List<QuanLyGiaoCaResponse> findAllGiaoCaAndOrderByTimeNhanCa();
}
