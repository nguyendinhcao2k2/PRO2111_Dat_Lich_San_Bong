package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ThoiGianDatLichAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThoiGianDatLichAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ThoiGianDatLichAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ThoiGianDatLichAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ThoiGianDatLichAdminServiceImpl implements ThoiGianDatLichAdminService {

    @Autowired
    private ThoiGianDatLichAdminRepository thoiGianDatLichAdminRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<ThoiGianDatLichAdminRespone> getAll(Pageable pageable) {
        try {
            Page<ThoiGianDatLich> thoiGianDatLich = thoiGianDatLichAdminRepository.findAll(pageable);
            TypeToken<Page<ThoiGianDatLichAdminRespone>> token = new TypeToken<Page<ThoiGianDatLichAdminRespone>>() {
            };
            return modelMapper.map(thoiGianDatLich, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(ThoiGianDatLichAdminRequets thoiGianDatLichAdminRequets) {
        try {
            ThoiGianDatLich thoiGianDatLich = modelMapper.map(thoiGianDatLichAdminRequets, ThoiGianDatLich.class);
            thoiGianDatLichAdminRepository.save(thoiGianDatLich);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (thoiGianDatLichAdminRepository.existsById(id)) {
                thoiGianDatLichAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ThoiGianDatLichAdminRequets thoiGianDatLichAdminRequets) {
        try {
            thoiGianDatLichAdminRepository.saveAndFlush(modelMapper.map(thoiGianDatLichAdminRequets, ThoiGianDatLich.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
