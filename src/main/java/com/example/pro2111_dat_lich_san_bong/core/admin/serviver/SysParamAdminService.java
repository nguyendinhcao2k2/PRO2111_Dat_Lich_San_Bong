package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminCreateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminUpdateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SysParamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;

import java.util.List;

public interface SysParamAdminService {
    void save(SysParamAdminCreateRequest sysParamAdminCreateRequest);

    SysParam update(SysParamAdminUpdateRequest sysParamAdminUpdateRequest);

    SysParamAdminResponse findFirstByCode(String code);

    SysParamAdminResponse findByID(String id);

    List<SysParamAdminResponse> getAll();

    void deleteById(String id);
}
