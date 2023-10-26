package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KetCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface IGiaoCaStaffService {

    Boolean khoiTaoCaLamViec(KhoiTaoCaRequest khoiTaoCaRequest);

    GiaoCaResponse findGiaoCaByTrangThai(TrangThaiGiaoCa trangThaiGiaoCa);

    GiaoCaResponse findFirstByOrderByThoiGianNhanCaDesc();

    GiaoCaResponse findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount);

    Integer tongSoHoaDonTrongCaFollowStatus(Integer trangThaiGC, Integer trangThaiHDSC);

    Double tongTienTrongCaTheoHinhThucThanhToan(Integer loaiHinhThanhToan);

    Boolean ketCa(KetCaRequest ketCaRequest);

    List<GiaoCaResponse> giaoCaList();

    Page<QuanLyGiaoCaResponse> findAllGiaoCaByStatus(Pageable pageable,TrangThaiGiaoCa status);

    Page<QuanLyGiaoCaResponse> searchByName(Pageable pageable, @Param("name") String name);

    Page<QuanLyGiaoCaResponse> giaoCaCoTienRut(Pageable pageable);

    Page<QuanLyGiaoCaResponse> giaoCaByThoiGianNhanCa(Pageable pageable, Date time);
}
