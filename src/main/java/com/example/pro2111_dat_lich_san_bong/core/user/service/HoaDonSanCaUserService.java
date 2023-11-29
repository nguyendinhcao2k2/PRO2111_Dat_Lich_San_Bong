package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface HoaDonSanCaUserService {

    void saveAllHoaDonSanCa(List list);

    void saveHoaDonSanCa(HoaDonSanCa hoaDonSanCa);

    void updateAll(List list);

    List findAllByIdHoaDon(String idHoaDon);

    void deleteAllByIdHoaDon(String idHoaDon);

    HoaDonSanCa findById(String id);

    void  deleteByIdHoaDonSanCa(String id);

    HoaDonSendMailResponse getDetialHoaDon(String idHoaDonSanCa);
    HoaDonSanCa findByIdSanCa(String id);

    List<HoaDonSendMailResponse> getLisTHDSC(String idHoaDon);

    List<HoaDonSanCa> findByIdHoaDon(String idHoaDon);

}
