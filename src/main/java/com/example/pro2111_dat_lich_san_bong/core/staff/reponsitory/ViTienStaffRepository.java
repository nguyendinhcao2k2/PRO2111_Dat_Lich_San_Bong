package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViTienStaffRepository extends JpaRepository<ViTienCoc, String> {
    ViTienCoc getViTienCocByIdHoaDon(String idHoaDon);
}
