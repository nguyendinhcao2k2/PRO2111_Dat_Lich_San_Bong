package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LoaiSanAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LoaiSanAdminUpdateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LoaiSanAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.request.LoaiSanRequest;

import java.util.List;

public interface LoaiSanAdminService {

    List<LoaiSanAdminRespone> getAll();

    String create(LoaiSanAdminRequets loaiSanAdminRequets);

    LoaiSanAdminRespone findById(String id);

    void deleteById(String id);

    void update(LoaiSanAdminUpdateRequest updateRequest);
}
