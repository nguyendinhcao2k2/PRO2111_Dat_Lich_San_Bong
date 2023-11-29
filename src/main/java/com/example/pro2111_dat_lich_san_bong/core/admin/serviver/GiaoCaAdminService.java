package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GiaoCaAdminService {
    Page<QuanLyGiaoCaResponse> findAllGiaoCaByStatus(Pageable pageable, TrangThaiGiaoCa status);

    Page<QuanLyGiaoCaResponse> searchByName(Pageable pageable, @Param("name") String name);

    Page<QuanLyGiaoCaResponse> giaoCaCoTienRut(Pageable pageable);

    Page<QuanLyGiaoCaResponse> giaoCaByThoiGianNhanCa(Pageable pageable, Date time);

    QuanLyGiaoCaResponse findGiaoCaByIdAndStatus(TrangThaiGiaoCa trangThaiGiaoCa, String id);

    List<QuanLyGiaoCaResponse> findAllGiaoCaAndOrderByTimeNhanCa();

    void  exportExcel(HttpServletResponse response,List<QuanLyGiaoCaResponse> list);
}
