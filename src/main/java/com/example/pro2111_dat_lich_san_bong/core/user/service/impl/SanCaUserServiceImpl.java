package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.SanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class SanCaUserServiceImpl implements SanCaUserService {

    @Autowired
    private SanCaUserRepository sanCaUserRepository;

    @Override
    public PageableObject getAllSanCa(SanCaUserRequest request) {
        return new PageableObject(sanCaUserRepository.getAllSanCa(request));
    }
}
