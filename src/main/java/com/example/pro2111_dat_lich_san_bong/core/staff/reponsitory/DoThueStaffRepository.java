package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoThueStaffRepository extends JpaRepository<DoThue, String> {
    List<DoThue> getAllByTrangThai(int trangThai);
}
