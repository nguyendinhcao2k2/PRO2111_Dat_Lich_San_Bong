package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminCreateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminUpdateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.SanBongAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SanBongAdminServiceImpl implements SanBongAdminService {

    @Autowired
    private SanBongAdminRepository sanBongAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<SanBongAdminRespone> getAll(Pageable pageable) {
        try {
            Page<SanBong> listSanBong = sanBongAdminRepository.findAllSanBongAndLuatSan(pageable);
            TypeToken<Page<SanBongAdminRespone>> token = new TypeToken<Page<SanBongAdminRespone>>() {
            };
            return modelMapper.map(listSanBong, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    @Transactional
    public void create(SanBongAdminCreateRequets sanBongAdminCreateRequets) {
        try {
            SanBong sanBong = modelMapper.map(sanBongAdminCreateRequets, SanBong.class);
            sanBongAdminRepository.save(sanBong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            if (sanBongAdminRepository.existsById(id)) {
                sanBongAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(SanBongAdminUpdateRequets sanBongAdminUpdateRequets) {
        try {
            sanBongAdminRepository.saveAndFlush(modelMapper.map(sanBongAdminUpdateRequets, SanBong.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SanBong findFirstByTenSanBong(String sanBong) {
        try {
            return sanBongAdminRepository.findFirstByTenSanBong(sanBong);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SanBongAdminRespone findByID(String id) {
        try {
            if (sanBongAdminRepository.existsById(id)) {
                return modelMapper.map(sanBongAdminRepository.findById(id).get(), SanBongAdminRespone.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<SanBongAdminRespone> findAllByTenSanBongContains(String tenSanBong, Pageable pageable) {
        try {
            Page<SanBong> listSanBong = sanBongAdminRepository.findAllByTenSanBongContains(tenSanBong, pageable);
            TypeToken<Page<SanBongAdminRespone>> token = new TypeToken<>() {
            };
            return modelMapper.map(listSanBong, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }
}
