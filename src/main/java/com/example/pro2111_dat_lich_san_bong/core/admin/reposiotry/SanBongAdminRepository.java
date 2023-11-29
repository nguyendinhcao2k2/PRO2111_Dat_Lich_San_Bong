package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SanBongAdminRepository extends SanBongRepository {
    SanBong findFirstByTenSanBong(String sanBong);

    Page<SanBong> findAllByTenSanBongContains(String tenSanBong, Pageable pageable);
}
