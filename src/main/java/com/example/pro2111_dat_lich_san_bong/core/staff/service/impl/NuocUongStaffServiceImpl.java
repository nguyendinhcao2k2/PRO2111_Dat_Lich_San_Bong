package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.NuocUongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.INuocUongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.NotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class NuocUongStaffServiceImpl implements INuocUongStaffService {
    @Autowired
    private NuocUongStaffRepository nuocUongStaffRepository;

    @Override
    public List<NuocUong> getNuocUongPagaeble() {
        return nuocUongStaffRepository.findAll();
    }

    @Override
    public NuocUong getOneNuocUong(String id) {
        Optional<NuocUong> nuocUong = nuocUongStaffRepository.findById(id);
        if (!nuocUong.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Nước Uống Này");
        }
        return nuocUongStaffRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        Optional<NuocUong> nuocUong = nuocUongStaffRepository.findById(id);
        if (!nuocUong.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Nước Uống Này");
        }
        nuocUongStaffRepository.deleteById(id);
    }

    @Override
    public boolean updateById(String id, NuocUong nuocUong) {
        Optional<NuocUong> nuocUongOptional = nuocUongStaffRepository.findById(id);
        if (!nuocUongOptional.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Nước Uống Này");
        }
        try {
            NuocUong nuocUongUpdate = new NuocUong();
            PropertyUtils.copyProperties(nuocUongUpdate, nuocUong);
            nuocUongStaffRepository.save(nuocUongUpdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean save(NuocUong nuocUong) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            NuocUong nuocUongUpdate = new NuocUong();
            PropertyUtils.copyProperties(nuocUongUpdate, nuocUong);
            nuocUongUpdate.setTrangThai(0);
            nuocUongStaffRepository.save(nuocUongUpdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
