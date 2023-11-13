package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.SYSParamUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
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

    @Override
    public Double getTienCoc(Double giaSanCa) {

        SysParam param = repository.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC);
        Double value = Double.valueOf(param.getValue());
        Double tienCoc = giaSanCa*(value/100);
        return tienCoc;
    }


}
