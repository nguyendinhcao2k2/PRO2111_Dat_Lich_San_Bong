package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;


import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminCreateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminUpdateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanBongAdminService {

    Page<SanBongAdminRespone> getAll(Pageable pageable);

    void create(SanBongAdminCreateRequets sanBongAdminCreateRequets);

    void delete(String id);

    void update( SanBongAdminUpdateRequets sanBongAdminUpdateRequets);

    SanBong findFirstByTenSanBong(String sanBong);

    SanBongAdminRespone findByID(String id);

    Page<SanBongAdminRespone> findAllByTenSanBongContains(String tenSanBong, Pageable pageable);
}
