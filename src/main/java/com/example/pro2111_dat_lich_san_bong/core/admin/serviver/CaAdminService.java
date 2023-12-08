package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.CaAdminReponse;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaAdminService {
    Page<CaAdminReponse> findAllCa(int size, int page);

    Page<CaAdminReponse> findByName(int size, int page, @Param("tenCa") String tenCa);

    void saveOrUpdate(CaAdminRequest caAdminRequest);

    void saveAll(List list);

    void deleteById(String id);

    Ca findById(String id);

    List<Ca> findAllListCa();
}
