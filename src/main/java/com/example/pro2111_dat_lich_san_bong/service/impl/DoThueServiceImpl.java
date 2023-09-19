package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.repository.DoThueRepository;
import com.example.pro2111_dat_lich_san_bong.service.DoThueService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DoThueServiceImpl implements DoThueService {

    @Autowired
    private DoThueRepository doThueRepository;


    @Override
    public List<DoThue> getAll() {
        return doThueRepository.findAll();
    }

    @Override
    public DoThue findById(String id) {
        return doThueRepository.findById(id).get();
    }

    @Override
    @Transactional
    public DoThue create(DoThueRequest doThueRequest) {
        DoThue doThue = new DoThue(null,doThueRequest.getTenDoThue(),doThueRequest.getSoLuong(),
                doThueRequest.getThoiGianMuon(), doThueRequest.getThoiGianTra(),doThueRequest.getDonGia(),
                doThueRequest.getTrangThai());
        return doThueRepository.save(doThue);
    }

    @Override
    @Transactional
    public void remove(String id) {
        doThueRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DoThue update(String id, DoThueRequest doThueRequest) {
        DoThue doThue = doThueRepository.findById(id).get();
        doThue.setTenDoThue(doThueRequest.getTenDoThue());
        doThue.setSoLuong(doThueRequest.getSoLuong());
        doThue.setThoiGianMuon(doThueRequest.getThoiGianMuon());
        doThue.setThoiGianTra(doThueRequest.getThoiGianTra());
        doThue.setDonGia(doThueRequest.getDonGia());
        doThue.setTrangThai(doThueRequest.getTrangThai());
        return doThueRepository.save(doThue);
    }

}
