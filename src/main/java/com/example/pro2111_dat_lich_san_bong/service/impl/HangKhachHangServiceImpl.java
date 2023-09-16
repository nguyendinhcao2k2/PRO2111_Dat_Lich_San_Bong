package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.HangKhachHang;
import com.example.pro2111_dat_lich_san_bong.model.request.HangKhachHangRequest;
import com.example.pro2111_dat_lich_san_bong.repository.HangKhachHangRepository;
import com.example.pro2111_dat_lich_san_bong.service.HangKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangKhachHangServiceImpl implements HangKhachHangService {

    @Autowired
    private HangKhachHangRepository hangKhachHangRepository;

    @Override
    public List<HangKhachHang> getAll() {
        return hangKhachHangRepository.findAll();
    }

    @Override
    public HangKhachHang findById(String id) {
        return hangKhachHangRepository.findById(id).get();
    }

    @Override
    public HangKhachHang create(HangKhachHangRequest hangKhachHangRequest) {
        HangKhachHang hangKhachHang = new HangKhachHang(null, hangKhachHangRequest.getTenHang(),
                hangKhachHangRequest.getDiemTichLuy(), hangKhachHangRequest.getTrang_thai());
        return hangKhachHangRepository.save(hangKhachHang);
    }

    @Override
    public void remove(String id) {
        hangKhachHangRepository.deleteById(id);
    }

    @Override
    public HangKhachHang update(String id, HangKhachHangRequest hangKhachHangRequest) {
        HangKhachHang hangKhachHang = hangKhachHangRepository.findById(id).get();
        hangKhachHang.setTenHang(hangKhachHangRequest.getTenHang());
        hangKhachHang.setDiemTichLuy(hangKhachHangRequest.getDiemTichLuy());
        hangKhachHang.setTrang_thai(hangKhachHangRequest.getTrang_thai());
        return hangKhachHangRepository.save(hangKhachHang);
    }
}
