package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KetCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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


}
