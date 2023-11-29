package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.LoaiSanUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface LoaiSanUserService {

    List getAllLoaiSan();

    List<LoaiSanUserResponse> findAll();

    LoaiSan finLoaiSanByTeLoaiSan(String tenLoaiSan);
}
