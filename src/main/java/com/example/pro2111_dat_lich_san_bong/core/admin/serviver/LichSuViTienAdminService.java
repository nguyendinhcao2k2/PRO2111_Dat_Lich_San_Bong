package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LichSuViTienAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LichSuViTienAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LichSuViTienAdminService {

    List<LichSuViTienAdminRespone>listAll();

    Page<LichSuViTienAdminRespone> getAll(Pageable pageable);

    void create(LichSuViTienAdminRequest lichSuViTienAdminRequest);

    void delete(String id);

    void update(LichSuViTienAdminRequest lichSuViTienAdminRequest);
}
