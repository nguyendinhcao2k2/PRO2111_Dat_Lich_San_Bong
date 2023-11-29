package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongTinAccountThongKeReponse;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface AccountAdminRepository extends AccountRepository {
    @Query(value = """
                select 
                a.mat_khau as 'matKhau' ,
                a.tai_khoan as 'taiKhoan' ,
                c.ten_chuc_vu as 'tenChucVu'
                from account a join chuc_vu c on a.id_chuc_vu = c.id
            """, nativeQuery = true)
    List<AccountAdminResponse> getALl();

    @Query("""
            SELECT new com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongTinAccountThongKeReponse(
            ac.email,ac.soDienThoai)
             FROM Account ac
                    join ChucVu cv on ac.idChucVu = cv.id
                    where cv.tenChucVu = :tenChucVu
            """)
    List<ThongTinAccountThongKeReponse> getAllAccountByChucVu(@Param("tenChucVu") String tenChucVu);
}
