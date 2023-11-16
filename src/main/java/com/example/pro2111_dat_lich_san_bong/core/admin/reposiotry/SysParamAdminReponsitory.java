package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.repository.SYSParamRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysParamAdminReponsitory extends SYSParamRepository {

    SysParam findFirstByCode(String code);
}
