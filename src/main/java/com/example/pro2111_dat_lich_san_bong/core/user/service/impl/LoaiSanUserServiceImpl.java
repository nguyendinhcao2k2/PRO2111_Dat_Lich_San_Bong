package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.LoaiSanUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.LoaiSanUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LoaiSanUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class LoaiSanUserServiceImpl implements LoaiSanUserService {

    @Autowired
    private LoaiSanUserRepository loaiSanUserRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List getAllLoaiSan() {
        return loaiSanUserRepository.getAllLoaiSan();
    }

    @Override
    public List<LoaiSanUserResponse> findAll() {
        try {
            List<LoaiSan> loaiSanList = loaiSanUserRepository.findAll();

            if (loaiSanList != null) {
                TypeToken<List<LoaiSanUserResponse>> token = new TypeToken<>() {
                };
                return mapper.map(loaiSanList, token.getType());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoaiSan finLoaiSanByTeLoaiSan(String tenLoaiSan) {
        try {
            return loaiSanUserRepository.findFirstByTenLoaiSan(tenLoaiSan);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
