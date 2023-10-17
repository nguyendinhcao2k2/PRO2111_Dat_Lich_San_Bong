package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;

public interface IGiaoCaStaffService {

    Boolean khoiTaoCaLamViec(KhoiTaoCaRequest khoiTaoCaRequest);

    GiaoCaResponse findGiaoCaByTrangThai(Integer trangThaiGiaoCa);

    GiaoCaResponse findFirstByOrderByThoiGianNhanCaDesc();

    GiaoCaResponse findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount);


}
