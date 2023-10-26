package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LuatSanRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LuatSanAdminService {



    Page<LuatSanResponse> getAll(Pageable pageable);

    void createLuatSan(LuatSanRequest luatSanRequest);

    void delete(String id);

    void update( LuatSanRequest luatSanRequest);
}
