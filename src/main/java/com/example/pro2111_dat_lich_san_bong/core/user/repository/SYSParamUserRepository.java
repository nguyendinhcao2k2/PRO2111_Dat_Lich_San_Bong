package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.repository.SYSParamRepository;

/**
 * @author thepvph20110
 */
public interface SYSParamUserRepository extends SYSParamRepository {

    SysParam findSysParamByCode(String code);
}
