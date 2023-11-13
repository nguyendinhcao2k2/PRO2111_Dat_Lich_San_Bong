package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DichVuSanBongAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DichVuSanBongAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DichVuSanBongAdminService {

    Page<DichVuSanBongAdminRespone> getAll(Pageable pageable);

    void create(DichVuSanBongAdminRequest dichVuSanBongAdminRequest);

    void delete(String id);

    void update( DichVuSanBongAdminRequest dichVuSanBongAdminRequest);

}
