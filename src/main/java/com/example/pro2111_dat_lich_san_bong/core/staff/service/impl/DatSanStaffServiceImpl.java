package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanBongStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.CaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caodinh
 */
@Service
public class DatSanStaffServiceImpl implements IDatSanStaffService {

    @Autowired
    private CaStaffRepository caStaffRepository;

    @Autowired
    private SanBongStaffRepository sanBongStaffRepository;

    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Override
    public List<LoadSanBongRespose> loadSanBong() {
        List<LoadSanBongRespose> loadSanBongResposeList = new ArrayList<>();
        for (SanBongStaffResponse sanBongStaffResponse : sanBongStaffRepository.getAllSanBong()) {
            LoadSanBongRespose loadSanBongRespose = new LoadSanBongRespose();
            loadSanBongRespose.setIdSanBong(sanBongStaffResponse.getIdSanBong());
            loadSanBongRespose.setTenSanBong(sanBongStaffResponse.getTenSanBong());
            loadSanBongRespose.setGiaSan(loadSanBongRespose.getGiaSan());
            loadSanBongRespose.setIdLoaiSan(loadSanBongRespose.getIdLoaiSan());
            loadSanBongRespose.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
            List<LoadCaResponse> loadCaResponses = new ArrayList<>();
            for (CaStaffResponse caStaffResponse: caStaffRepository.getCa()) {
                LoadCaResponse loadCaResponse = new LoadCaResponse();
                loadCaResponse.setIdCa(caStaffResponse.getIdCa());
                loadCaResponse.setTenCa(caStaffResponse.getTenCa());
                loadCaResponse.setThoiGianBatDau(caStaffResponse.getThoiGianBatDau().toString());
                loadCaResponse.setThoiGianKetthuc(caStaffResponse.getThoiGianKetThuc().toString());
                loadCaResponse.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
                loadCaResponse.setGia(caStaffResponse.getGiaCa());
                loadCaResponses.add(loadCaResponse);
            }
            loadSanBongRespose.setLoadCaResponses(loadCaResponses);
            loadSanBongResposeList.add(loadSanBongRespose);
        }
        return loadSanBongResposeList;
    }
}
