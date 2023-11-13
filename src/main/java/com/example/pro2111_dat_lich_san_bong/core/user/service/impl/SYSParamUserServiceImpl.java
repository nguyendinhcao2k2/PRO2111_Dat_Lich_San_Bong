package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.SYSParamUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class SYSParamUserServiceImpl implements SYSParamUserService {

    @Autowired
    private SYSParamUserRepository repository;

    @Override
    public SysParam findSysParamByCode(String code) {
        return repository.findSysParamByCode(code);
    }
}
