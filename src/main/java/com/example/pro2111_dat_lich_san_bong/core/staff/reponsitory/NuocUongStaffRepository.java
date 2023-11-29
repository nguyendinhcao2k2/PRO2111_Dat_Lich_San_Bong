package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NuocUongStaffRepository extends JpaRepository<NuocUong, String> {
    List<NuocUong> getAllByTrangThai(int trangThai);
}
