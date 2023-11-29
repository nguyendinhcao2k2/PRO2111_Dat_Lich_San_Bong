package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.PhuPhiHoaDonAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.PhuPhiHoaDonAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhuPhiHoaDonAdminService {

    Page<PhuPhiHoaDonAdminRespone> getAll(Pageable pageable);

    void create(PhuPhiHoaDonAdminRequest phuPhiHoaDonAdminRequest);

    void delete(String id);

    void update( PhuPhiHoaDonAdminRequest phuPhiHoaDonAdminRequest);

}
