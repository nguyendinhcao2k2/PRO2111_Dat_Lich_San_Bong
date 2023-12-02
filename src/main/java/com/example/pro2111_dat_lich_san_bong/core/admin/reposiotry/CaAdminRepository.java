package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.CaAdminReponse;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaAdminRepository extends CaRepository {


     @Query(value = """
       select c.id as id, c.ten_ca as tenCa, c.thoi_gian_bat_dau as thoiGianBatDau, c.thoi_gian_ket_thuc as thoiGianKetThuc, c.gia_ca as giaCa, c.trang_thai as trangThai from ca c
""",nativeQuery = true)
     Page<CaAdminReponse> findAllCa(Pageable pageable);

     @Query(value = """
       select c.id as id, c.ten_ca as tenCa, c.thoi_gian_bat_dau as thoiGianBatDau, c.thoi_gian_ket_thuc as thoiGianKetThuc, c.gia_ca as giaCa, c.trang_thai as trangThai from ca c where c.ten_ca LIKE LOWER(CONCAT('%',:tenCa,'%'))
""",nativeQuery = true)
     Page<CaAdminReponse> findByName(Pageable pageable, @Param("tenCa") String tenCa);
}
