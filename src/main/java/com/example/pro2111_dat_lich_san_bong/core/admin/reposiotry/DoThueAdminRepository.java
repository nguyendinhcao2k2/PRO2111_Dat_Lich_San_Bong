package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author caodinh
 */
public interface DoThueAdminRepository extends JpaRepository<DoThue, String> {

    @Query(value = "SELECT id as id,don_gia as donGia,so_luong as soLuong,ten_do_thue as tenDoThue FROM do_thue;", nativeQuery = true)
    Page<DoThueResponse> findAllDoThue(PageRequest request);

}
