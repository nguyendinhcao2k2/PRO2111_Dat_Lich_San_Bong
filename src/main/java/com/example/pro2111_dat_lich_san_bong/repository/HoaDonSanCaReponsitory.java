package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonSanCaReponsitory extends JpaRepository<HoaDonSanCa, String> {

    HoaDonSanCa findHoaDonSanCaByIdSanCa(String idSanCa);

    @Query(value = """
       SELECT
           hd_sc.id,
           hd.ten_nguoi_dat as 'displayName',
           hd.email,
           c.thoi_gian_bat_dau as 'thoiGianBatDau',
           c.thoi_gian_ket_thuc as 'thoiGianKetThuc',
           hd.ngay_tao as 'ngayTao',
           hd_sc.ngay_den_san as 'ngayDenSan',
           hd_sc.tien_san as 'tienSan',
           hd_sc.ma_qr as 'maQR'
       FROM hoa_don_san_ca hd_sc
       join hoa_don hd on hd.id = hd_sc.id_hoa_don
       join account acc on acc.id = hd.id_account
       JOIN san_ca sc ON sc.id = hd_sc.id_san_ca
       join ca c on c.id = sc.id_ca
       WHERE sc.id_ca = :idCa AND DATE(hd_sc.ngay_den_san) = CURDATE() 
       and  hd_sc.trang_thai = :trangThai
    """,nativeQuery = true)
    List<HoaDonSendMailResponse> listHoaDonSanCaByIdCaAndNow(@Param("idCa") String idCa, @Param("trangThai")Integer trangThai);
}
