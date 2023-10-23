package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanBongStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanCaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.CaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<LoadSanBongRespose> loadSanBong(String date) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (!date.isBlank() || !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime = LocalDateTime.parse(date, formatter);
        }
        Map<String, Integer> checkSanCa = addDataToHashMap(localDateTime);
        List<LoadSanBongRespose> loadSanBongResposeList = new ArrayList<>();
        List<CaStaffResponse> caStaffResponses = caStaffRepository.getCa();
        for (SanBongStaffResponse sanBongStaffResponse : sanBongStaffRepository.getAllSanBong()) {
            LoadSanBongRespose loadSanBongRespose = new LoadSanBongRespose();
            loadSanBongRespose.setIdSanBong(sanBongStaffResponse.getIdSanBong());
            loadSanBongRespose.setTenSanBong(sanBongStaffResponse.getTenSanBong());
            loadSanBongRespose.setGiaSan(sanBongStaffResponse.getGiaSan());
            loadSanBongRespose.setIdLoaiSan(sanBongStaffResponse.getIdLoaiSan());
            loadSanBongRespose.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
            List<LoadCaResponse> loadCaResponses = new ArrayList<>();
            for (CaStaffResponse caStaffResponse : caStaffResponses) {
                LoadCaResponse loadCaResponse = new LoadCaResponse();
                loadCaResponse.setIdCa(caStaffResponse.getIdCa());
                loadCaResponse.setTenCa(caStaffResponse.getTenCa());
                loadCaResponse.setThoiGianBatDau(caStaffResponse.getThoiGianBatDau().toString());
                loadCaResponse.setThoiGianKetthuc(caStaffResponse.getThoiGianKetThuc().toString());
                loadCaResponse.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
                loadCaResponse.setGia(caStaffResponse.getGiaCa() + sanBongStaffResponse.getGiaSan());
                loadCaResponse.setDate(localDateTime.toLocalDate().toString());
                checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse);
                loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                loadCaResponses.add(loadCaResponse);
            }
            loadSanBongRespose.setLoadCaResponses(loadCaResponses);
            loadSanBongResposeList.add(loadSanBongRespose);
        }
        return loadSanBongResposeList;
    }

    public Map<String, Integer> addDataToHashMap(LocalDateTime localDateTime) {
        System.out.println(localDateTime.toString());
        Map<String, Integer> hashMap = new HashMap<>();
        LocalDateTime from = localDateTime.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999);
        List<SanCaStaffResponse> caStaffResponses = sanCaStaffRepository.findAllByDate(from, to);
        caStaffResponses.stream().forEach(cf -> hashMap.put(cf.getIdSanBong() + cf.getThoiGianBatDau(), cf.getTrangThai()));
        return hashMap;
    }

    public boolean checkTimeSanCa(Map<String, Integer> hashMap, String checkString, LoadCaResponse loadCaResponse) {
        if (hashMap.containsKey(checkString)) {
            loadCaResponse.setTrangThai(hashMap.get(checkString));
            return true;
        }
        loadCaResponse.setTrangThai(null);
        return false;
    }
}
