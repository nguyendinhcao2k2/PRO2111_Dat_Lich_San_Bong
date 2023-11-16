package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HangKhachHangAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.HangKhachHangAdminRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HangKhachHangAdminService {

    Page<HangKhachHangAdminRespone> getAll(Pageable pageable);

    void create(HangKhachHangAdminRequets hangKhachHangAdminRequets);

    void delete(String id);

    void update(HangKhachHangAdminRequets hangKhachHangAdminRequets);

}
