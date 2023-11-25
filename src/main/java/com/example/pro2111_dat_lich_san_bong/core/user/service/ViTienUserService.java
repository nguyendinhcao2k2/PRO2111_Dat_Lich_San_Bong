package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;

/**
 * @author thepvph20110
 */
public interface ViTienUserService {

    ViTienCoc saveViTen(ViTienCoc viTienCoc);

    ViTienCoc updateViTien(ViTienCoc viTienCoc);

    ViTienCoc findByIdHoaDon(String idHoaDon);

    ViTienCoc findById(String id);
}
