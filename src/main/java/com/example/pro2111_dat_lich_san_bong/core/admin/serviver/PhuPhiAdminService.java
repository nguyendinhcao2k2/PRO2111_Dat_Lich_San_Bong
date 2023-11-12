package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.PhuPhiAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiAdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhuPhiAdminService {

    Page<PhuPhiAdminResponse> getAll(Pageable pageable);

    void create(PhuPhiAdminRequest phuPhiAdminRequest);

    void delete(String id);

    void update( PhuPhiAdminRequest phuPhiAdminRequest);
}
