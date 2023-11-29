package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.PhuPhiAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanCaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanCaAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanCaAdminService {
    Page<SanCaAdminRespone> getAll(Pageable pageable);

    void create(SanCaAdminRequest sanCaAdminRequest);

    void delete(String id);

    void update( SanCaAdminRequest sanCaAdminRequest);
}
