package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.NuocUongResponse;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author caodinh
 */
public interface NuocUongAdminRepository extends JpaRepository<NuocUong, String> {

    @Query(value = "SELECT id as 'id',don_gia as 'donGia',so_luong as 'soLuong',ten_nuoc_uong as 'tenNuocUong',image as image,trang_thai as trangThai FROM nuoc_uong", nativeQuery = true)
    Page<NuocUongResponse> findAllNuocUong(PageRequest request);

    @Query(value = "SELECT id as id,don_gia as donGia,so_luong as soLuong,ten_nuoc_uong as tenNuocUong,image as image,trang_thai as trangThai FROM nuoc_uong where ten_nuoc_uong like LOWER(CONCAT('%', :tenNuocUong, '%'))", nativeQuery = true)
    Page<NuocUongResponse> findAllByName(PageRequest request, @Param("tenNuocUong") String tenNuocUong);
}
