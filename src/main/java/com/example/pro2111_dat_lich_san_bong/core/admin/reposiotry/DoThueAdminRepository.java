package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author caodinh
 */
public interface DoThueAdminRepository extends JpaRepository<DoThue, String> {

    @Query(value = "SELECT id as id,don_gia as donGia,so_luong as soLuong,ten_do_thue as tenDoThue,image as image, trang_thai as trangThai FROM do_thue;", nativeQuery = true)
    Page<DoThueResponse> findAllDoThue(PageRequest request);

    @Query(value = "SELECT id as id,don_gia as donGia,so_luong as soLuong,ten_do_thue as tenDoThue,image as image,  trang_thai as trangThai FROM do_thue where ten_do_thue like LOWER(CONCAT('%', :tenDoThue, '%'))", nativeQuery = true)
    Page<DoThueResponse> findAllByName(PageRequest request, @Param("tenDoThue") String tenDoThue);
}
