package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.SysParam;

/**
 * @author thepvph20110
 */
public interface SYSParamUserService {

    SysParam findSysParamByCode(String code);

    Double getTienCoc(Double giaSanCa);
}
