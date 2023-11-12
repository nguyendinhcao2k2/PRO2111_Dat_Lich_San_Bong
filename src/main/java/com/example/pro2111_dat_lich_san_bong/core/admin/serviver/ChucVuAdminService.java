package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ChucVuAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ChucVuAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ChucVuAdminService {

    Page<ChucVuAdminRespone> getAll(Pageable pageable);

    void create(ChucVuAdminRequest chucVuAdminRequest);

    void delete(String id);

    void update( ChucVuAdminRequest ChucVuAdminRequest);
}
