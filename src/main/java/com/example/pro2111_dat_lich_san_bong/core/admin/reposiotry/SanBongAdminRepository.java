package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanBongAdminRepository extends SanBongRepository {
    SanBong findFirstByTenSanBong(String sanBong);

    @Query("""
        SELECT new com.example.pro2111_dat_lich_san_bong.entity.SanBong(sb.id,sb.tenSanBong,ls.giaSan,sb.idLoaiSan,sb.trangThai) FROM SanBong sb
                join LoaiSan ls on sb.idLoaiSan = ls.id
""")
    Page<SanBong> findAllSanBongAndLuatSan(Pageable pageable);

    Page<SanBong> findAllByTenSanBongContains(String tenSanBong, Pageable pageable);
}
