package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.request.GiaoCaRequest;

public interface IGiaoCaService {
    GiaoCa findGiaoCaByTrangThai(Integer trangThai);

    Boolean khoiTaoCaLam(GiaoCaRequest giaoCaRequest);
}
