package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ThoiGianDatLichAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThoiGianDatLichAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThoiGianDatLichAdminService {

    Page<ThoiGianDatLichAdminRespone> getAll(Pageable pageable);

    void create(ThoiGianDatLichAdminRequets thoiGianDatLichAdminRequets);

    void delete(String id);

    void update(ThoiGianDatLichAdminRequets thoiGianDatLichAdminRequets);
}
