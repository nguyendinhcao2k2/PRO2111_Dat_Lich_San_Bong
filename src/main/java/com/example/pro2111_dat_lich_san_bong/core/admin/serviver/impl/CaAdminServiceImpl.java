package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.CaAdminReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.CaAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.CaAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaAdminServiceImpl implements CaAdminService {

    @Autowired
    private CaAdminRepository caAdminRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<CaAdminReponse> findAllCa(int size, int page) {
        try {
            Sort sort = Sort.by("thoiGianBatDau").ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return caAdminRepository.findAllCa(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<CaAdminReponse> findByName(int size, int page, String tenCa) {
       try {
        Sort sort = Sort.by("thoiGianBatDau");
        Pageable pageable = PageRequest.of(page, size,sort);
        return caAdminRepository.findByName(pageable,tenCa);
       }catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    @Override
    @Transactional
    public void saveOrUpdate(CaAdminRequest caAdminRequest) {
        try {
            if (caAdminRequest != null) {
                Ca ca = mapper.map(caAdminRequest, Ca.class);
                caAdminRepository.saveAndFlush(ca);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveAll(List list) {
        try {
            caAdminRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void deleteById(String id) {
        try {
            if (caAdminRepository.existsById(id)) {
                caAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ca findById(String id) {
        try {
            Ca ca = caAdminRepository.findById(id).get();
            if (ca != null) {
                return ca;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ca> findAllListCa() {
        try {
            Sort sort = Sort.by("thoiGianBatDau").ascending();
            return caAdminRepository.findAll(sort);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
