package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.DichVu;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DichVuService {
    List<DichVu> getAllDichVu();

    DichVu getOneDichVu(String id);

    Boolean createNewDichVu(DichVu dichVu);

    Boolean updateDichVu(String id, DichVu dichVu);

    Boolean deleteDichVu(String id);
}
