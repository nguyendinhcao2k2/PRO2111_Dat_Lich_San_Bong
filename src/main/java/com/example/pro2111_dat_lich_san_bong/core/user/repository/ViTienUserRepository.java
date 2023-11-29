package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;

/**
 * @author thepvph20110
 */
public interface ViTienUserRepository extends ViTienRepository {
    ViTienCoc findAllByIdHoaDon(String idHoaDon);
}
