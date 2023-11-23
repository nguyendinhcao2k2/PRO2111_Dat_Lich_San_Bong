package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface HoaDonSanCaUserRepository extends HoaDonSanCaReponsitory {

    List findAllByIdHoaDon(String idHoaDon);

    @Modifying
    @Query(value = """
        delete from hoa_don_san_ca where id_hoa_don =:request
    """,nativeQuery = true)
    void deleteAllByIdHoaDon(@Param("request")String idHoaDon);

    @Query(value = """
       SELECT
           hd_sc.id,
           acc.display_name as 'displayName',
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
       WHERE hd_sc.id = :req
    """,nativeQuery = true)
    HoaDonSendMailResponse getDetialHoaDon(@Param("req") String idHoaDonSanCa);
}
