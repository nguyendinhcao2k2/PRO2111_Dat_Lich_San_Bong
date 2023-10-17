package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoCaStaffReponsitory extends GiaoCaRepository {

    GiaoCa findGiaoCaByTrangThai(Integer trangThaiGiaoCa);

    GiaoCa findFirstByOrderByThoiGianNhanCaDesc();

    GiaoCa findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount);

}
