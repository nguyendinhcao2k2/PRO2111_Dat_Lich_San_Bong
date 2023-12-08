package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CheckInResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface HoaDonStaffService {

    List<CheckInResponse> listCheckIn(String param);

    ResponseEntity<?> checkIn(String param);

    HoaDon getHoaDonById(String idHoaDon);

    HoaDon updateHoaDon(HoaDon hoaDon);

    Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCa(Pageable pageable);

    Page<LichSuHoaDonStaffReponse> findAllDataHoaDonAndHoaDonSanCaUser(Pageable pageable, String idAccount);

    Page<LichSuHoaDonStaffReponse> searchLichSuHoaDon(Pageable pageable,String ten, String soDienThoaiNguoiDat);

    Page<LichSuHoaDonStaffReponse> searchLichSuHoaDonUser(Pageable pageable,String idAccount, String ten,String soDienThoaiNguoiDat);
}
