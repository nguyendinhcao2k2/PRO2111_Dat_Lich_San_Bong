package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPhuPhiHoaDonStaffService {

    List<PhuPhiHoaDonRequest> getPhuPhiHoaDonByIdSanCa(String idHoaDonSanCa,Integer trangThai);
}
