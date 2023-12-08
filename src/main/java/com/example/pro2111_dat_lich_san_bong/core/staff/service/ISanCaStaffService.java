package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.EventStaffRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface ISanCaStaffService {

    List getAllSanCa(EventStaffRequest request);

    int checkSanCa(String idLoaiSan,String idCa,String ngayDat);

    List checkListSanCa(List<CheckSanCaUserRequest> list);

    void saveAllSanCa(List list);

    SanCa findSanCaById(String id);

    void deleteSanCaById(String idSanCa);

    void saveSanCa(SanCa sanCa);
}
