package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface LichSuSanBongAdminService {
    void createOrUpdate(LichSuSanBong lichSuSanBong);

    void saveAll(List list);

    Integer thongKeSoLuot(Integer trangThaiLichSu, LocalDate ngayThucHien);

    Integer thongKeSoLuotTheoTuan(Integer trangThaiLichSu, Integer yearWeek);

    Integer thongKeSoLuotTheoThang(Integer trangThaiLichSu, LocalDate ngayThucHien);

    Integer thongKeSoLuotTheoNam(Integer trangThaiLichSu, Integer namThucHien);

}
