package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanCaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ViTienCocAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanCaAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ViTienCocAdminService {
    Page<ViTienCocAdminRespone> getAll(Pageable pageable);

    void create(ViTienCocAdminRequest viTienCocAdminRequest);

    void delete(String id);

    void update( ViTienCocAdminRequest viTienCocAdminRequest);
}
