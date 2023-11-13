package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HangKhachHangAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DichVuSanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.HangKhachHangAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.DichVuSanBongAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.HangKhachHangAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HangKhachHangAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HangKhachHang;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HangKhachHangAdminServiceImpl implements HangKhachHangAdminService {

    @Autowired
    private HangKhachHangAdminRepository hangKhachHangAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<HangKhachHangAdminRespone> getAll(Pageable pageable) {
        try {
            Page<HangKhachHang> hangKhachHangs = hangKhachHangAdminRepository.findAll(pageable);
            TypeToken<Page<HangKhachHangAdminRespone>> token = new TypeToken<Page<HangKhachHangAdminRespone>>() {
            };
            return modelMapper.map(hangKhachHangs, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(HangKhachHangAdminRequets hangKhachHangAdminRequets) {
        try {
            HangKhachHang hangKhachHang = modelMapper.map(hangKhachHangAdminRequets, HangKhachHang.class);
            hangKhachHangAdminRepository.save(hangKhachHang);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (hangKhachHangAdminRepository.existsById(id)) {
                hangKhachHangAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(HangKhachHangAdminRequets hangKhachHangAdminRequets) {
        try {
            hangKhachHangAdminRepository.saveAndFlush(modelMapper.map(hangKhachHangAdminRequets, HangKhachHang.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
