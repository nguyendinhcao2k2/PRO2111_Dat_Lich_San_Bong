package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface SanCaUserService {

    List getAllSanCa(EventRequest request);

    int checkSanCa(String idLoaiSan,String idCa,String ngayDat);

    List checkListSanCa(List<CheckSanCaUserRequest> list);

    void saveAllSanCa(List list);

    SanCa findSanCaById(String id);

    void deleteSanCaById(String idSanCa);

    void saveSanCa(SanCa sanCa);

}
