package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanBongUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.SanBongUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class SanBongUserServiceImpl implements SanBongUserService {

    @Autowired
    private SanBongUserRepository sanBongUserRepository;


    @Override
    public PageableObject<SanBongUserResponse> getAllSanBong(Pageable page) {
        Page respo = sanBongUserRepository.getAllSanBong(page);
        return new PageableObject<SanBongUserResponse>(respo);
    }

    @Override
    public Integer countSanBong(String idLoaiSan) {
        return sanBongUserRepository.countSanBong(idLoaiSan);
    }

    @Override
    public List getALlSanBongByIdLoaiSan(String idLoaiSan) {
        return sanBongUserRepository.findAllByIdLoaiSan(idLoaiSan);
    }
}
