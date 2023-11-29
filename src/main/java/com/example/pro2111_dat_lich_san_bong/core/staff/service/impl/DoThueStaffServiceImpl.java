package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DoThueStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDoThueStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.NotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class DoThueStaffServiceImpl implements IDoThueStaffService {
    @Autowired
    private DoThueStaffRepository doThueStaffRepository;

    @Override
    public List<DoThue> getDoThuePagaeble() {
        return doThueStaffRepository.findAll();
    }

    @Override
    public DoThue getOneDoThue(String id) {
        Optional<DoThue> doThue = doThueStaffRepository.findById(id);
        if (!doThue.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Đồ Thuê Này !");
        }
        return doThueStaffRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        Optional<DoThue> doThue = doThueStaffRepository.findById(id);
        if (!doThue.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Đồ Thuê Này !");
        }
        doThueStaffRepository.deleteById(id);
    }

    @Override
    public boolean updateById(String id, DoThue doThue) {
        Optional<DoThue> doThueOptional = doThueStaffRepository.findById(id);
        if (!doThueOptional.isPresent()) {
            throw new NotFoundException("Không Tìm Thấy Đồ Thuê Này !");
        }
        try {
            DoThue doThueUpdate = new DoThue();
            PropertyUtils.copyProperties(doThueUpdate, doThue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean save(DoThue doThue) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            DoThue doThueSave = new DoThue();
            PropertyUtils.copyProperties(doThueSave, doThue);
            doThueSave.setTrangThai(0);
            doThueStaffRepository.save(doThueSave);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
