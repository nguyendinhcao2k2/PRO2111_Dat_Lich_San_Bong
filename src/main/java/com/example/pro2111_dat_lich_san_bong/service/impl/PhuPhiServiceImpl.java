package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.PhuPhiRequest;
import com.example.pro2111_dat_lich_san_bong.repository.PhuPhiRepository;
import com.example.pro2111_dat_lich_san_bong.service.PhuPhiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhuPhiServiceImpl implements PhuPhiService {

    @Autowired
    private PhuPhiRepository phuPhiRepository;

    @Override
    public List<PhuPhi> finAll() {
        return phuPhiRepository.findAll();
    }

    @Override
    public Optional<PhuPhi> findById(String id) {
        return phuPhiRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        phuPhiRepository.deleteById(id);
    }

    @Override
    public PhuPhi update(String id, PhuPhiRequest phuPhiRequest) {
        Optional<PhuPhi> phuPhi = phuPhiRepository.findById(id);

         return phuPhi.map(phuPhiNew -> {
            phuPhiNew.setTenPhuPhi(phuPhiRequest.getTenPhuPhi());
            phuPhiNew.setGiaPhuPhi(phuPhiRequest.getGiaPhuPhi());
            phuPhiNew.setTrangThai(phuPhiRequest.getTrangThai());
            return phuPhiRepository.save(phuPhiNew);
        }).orElseThrow(
                () -> new RestApiException("Not Found Phu Phi Width Id : " + id)
        );
    }

    @Override
    public PhuPhi create(PhuPhiRequest phuPhiRequest) {
        return phuPhiRepository.save(
                PhuPhi.builder()
                        .tenPhuPhi(phuPhiRequest.getTenPhuPhi())
                        .giaPhuPhi(phuPhiRequest.getGiaPhuPhi())
                        .maPhuPhi(genCodeAuto())
                        .trangThai(phuPhiRequest.getTrangThai())
                        .build()
        );
    }

    @Override
    public String genCodeAuto() {
        String codePhuPhi = "";
        codePhuPhi = phuPhiRepository.genCodePhuPhi().toString();
        if (codePhuPhi == "") {
            codePhuPhi = "1";
            Integer code = Integer.valueOf(codePhuPhi);
            return "PP00" + code;
        }
        Integer code = Integer.valueOf(codePhuPhi);
        return "PP00" + ++code;

    }
}
