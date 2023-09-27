package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;

public interface IGiaoCaService {
    GiaoCa findGiaoCaByTrangThai(Integer trangThai);

    Boolean khoiTaoCaLam(GiaoCa giaoCa);
}
