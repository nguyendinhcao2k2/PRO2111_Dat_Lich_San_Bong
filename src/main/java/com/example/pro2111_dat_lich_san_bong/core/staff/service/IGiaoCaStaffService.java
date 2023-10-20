package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KetCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import org.springframework.data.repository.query.Param;

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
