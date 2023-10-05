package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.repository.GiaoCaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoCaStaffReponsitory extends GiaoCaRepository {

    GiaoCa findGiaoCaByTrangThai(Integer trangThai);

    @Query(value = """
                   select sum(hd.tong_tien) as TongTienTrongCa from giao_ca gc
                   join account ac on gc.id_account = ac.id
                   join phieu_dat_lich ph on ac.id = ph.id_account
                   join hoa_don hd on ph.id = hd.id_phieu_dat_lich
                   join hoa_don_thanh_toan hdtt on hd.id = hdtt.id_hoa_don
                   join thanh_toan tt on hdtt.id_thanh_toan = tt.id
                   where gc.id_nhan_vien_trong_ca = :idNhanVienTrongCa and hd.ngay_thanh_toan between gc.thoi_gian_nhan_ca and curdate()
                   group by ac.id
            """, nativeQuery = true)
    Double tongTienCaHienTai(@Param("idNhanVienTrongCa") String idNhanVienTrongCa);

}
