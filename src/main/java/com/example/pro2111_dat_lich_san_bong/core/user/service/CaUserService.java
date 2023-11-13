package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaDoiLichUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface CaUserService {

    List getAllCaByIdLoaiSan(String idLoaiSan);

    CaUserResponse getCaByIdCa(String idCa,String idLoaiSan);

    List<CaDoiLichUserResponse> findAll();
    Ca findFirstByTenCa(String tenCa);
}
