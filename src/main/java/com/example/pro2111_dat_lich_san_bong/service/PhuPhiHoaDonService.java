package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PhuPhiHoaDonService {
    List<PhuPhiHoaDon> getAllPhuPhiHoaDons();

    PhuPhiHoaDon getPhuPhiHoaDonsById(String id);

    Boolean createNewPhuPhiHoaDon(PhuPhiHoaDon phuPhiHoaDon);

    Boolean updatePhuPhiHoaDon(String id, PhuPhiHoaDon phuPhiHoaDon);

    Boolean deletePhuPhiHoaDon(String id);
}
