package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LoaiSanAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LoaiSanAdminUpdateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LoaiSanAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.LoaiSanAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LoaiSanAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.request.LoaiSanRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSanAdminServiceImpl implements LoaiSanAdminService {

    @Autowired
    private LoaiSanAdminRepository loaiSanAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LoaiSanAdminRespone> getAll() {
        try {
            List<LoaiSan> getAll = loaiSanAdminRepository.findAll(Sort.by("tenLoaiSan").ascending());
            TypeToken<List<LoaiSanAdminRespone>> token = new TypeToken<>() {
            };
            return modelMapper.map(getAll, token.getType());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String create(LoaiSanAdminRequets loaiSanAdminRequets) {
        try {
            LoaiSan loaiSan = modelMapper.map(loaiSanAdminRequets, LoaiSan.class);
            loaiSan.setTrangThai(0);
            loaiSanAdminRepository.save(loaiSan);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LoaiSanAdminRespone findById(String id) {
        try {
            if (loaiSanAdminRepository.existsById(id)) {
                LoaiSan loaiSan = loaiSanAdminRepository.findById(id).get();
                return modelMapper.map(loaiSan, LoaiSanAdminRespone.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            if (loaiSanAdminRepository.existsById(id)) {
                loaiSanAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LoaiSanAdminUpdateRequest updateRequest) {
        try {
            if (loaiSanAdminRepository.existsById(updateRequest.getId())) {
                LoaiSan loaiSan = modelMapper.map(updateRequest, LoaiSan.class);
                loaiSanAdminRepository.saveAndFlush(loaiSan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
