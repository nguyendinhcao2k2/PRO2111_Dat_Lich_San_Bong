package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminCreateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminUpdateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SysParamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.SysParamAdminReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SysParamAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysParamAdminServiceImpl implements SysParamAdminService {

    @Autowired
    private SysParamAdminReponsitory sysParamAdminReponsitory;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void save(SysParamAdminCreateRequest sysParamAdminCreateRequest) {
        try {
            SysParam sysParam = mapper.map(sysParamAdminCreateRequest, SysParam.class);
            sysParamAdminReponsitory.save(sysParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SysParamAdminUpdateRequest sysParamAdminUpdateRequest) {
        try {
            SysParam sysParam = mapper.map(sysParamAdminUpdateRequest, SysParam.class);
            sysParamAdminReponsitory.saveAndFlush(sysParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SysParamAdminResponse findFirstByCode(String code) {
        try {
            SysParam sysParam = sysParamAdminReponsitory.findFirstByCode(code);
            if (sysParam != null) {
                return mapper.map(sysParam, SysParamAdminResponse.class);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SysParamAdminResponse findByID(String id) {
        try {
            SysParam sysParam = sysParamAdminReponsitory.findById(id).get();
            if (sysParam != null) {
                return mapper.map(sysParam, SysParamAdminResponse.class);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SysParamAdminResponse> getAll() {
        try {
            List<SysParam> sysParamList = sysParamAdminReponsitory.findAll();
            TypeToken<List<SysParamAdminResponse>> token = new TypeToken<>() {
            };
            if (sysParamList != null) {
                return mapper.map(sysParamList, token.getType());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            if (sysParamAdminReponsitory.existsById(id)) {
                sysParamAdminReponsitory.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
