package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanBongUserResponse;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
/**
 * @author thepvph20110
 */

public interface SanBongUserRepository extends SanBongRepository {

    @Query(value = """
        select  sb.id , sb.ten_san_bong as 'tenSanBong',\s
        sb.gia_san as 'giaSan',sb.trang_thai as 'trangThai',
        ls.ten_loai_san as'tenLoaiSan'\s
        from san_bong sb\s
        join loai_san ls on sb.id_loai_san = ls.id
    """,countQuery = """
        select  count(*)
        from san_bong sb
        join loai_san ls on sb.id_loai_san = ls.id
    """,nativeQuery = true)
    Page<SanBongUserResponse> getAllSanBong(Pageable page);
}
