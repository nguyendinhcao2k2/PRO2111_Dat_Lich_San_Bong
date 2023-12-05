package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.DoiLichNhieuStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoiLichNhieuUserService {

    List<DoiLichNhieuUserResponse> findListLichDoi(Integer timeChoPhepDoiLich, String idNguoiDat);
    Page<DoiLichNhieuStaffReponse> findListLichDoiDatHo(Integer timeChoPhepDoiLich, Pageable pageable);
    Page<DoiLichNhieuStaffReponse> tinKiemLichDatHoTheoSDT(Integer timeChoPhepDoiLich,String soDienThoai, Pageable pageable);
}
