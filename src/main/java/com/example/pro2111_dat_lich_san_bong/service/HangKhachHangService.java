package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.HangKhachHang;
import com.example.pro2111_dat_lich_san_bong.model.request.CaRequest;
import com.example.pro2111_dat_lich_san_bong.model.request.HangKhachHangRequest;

import java.util.List;

public interface HangKhachHangService {
    List<HangKhachHang> getAll();

    HangKhachHang findById(String id);

    HangKhachHang create(HangKhachHangRequest hangKhachHangRequest);

    void remove(String id);

    HangKhachHang update(String id, HangKhachHangRequest hangKhachHangRequest);
}
