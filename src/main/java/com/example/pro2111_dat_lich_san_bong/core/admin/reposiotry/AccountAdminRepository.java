package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountAdminResponse;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface AccountAdminRepository extends AccountRepository {
    @Query(value = """
        select 
        a.mat_khau as 'matKhau' ,
        a.taiKhoan as 'taiKhoan' ,
        c.ten_chu_vu as 'tenChucVu'
        from account a join chuc_vu c on a.id_chuc_vu = c.id
    """, nativeQuery=true)
    List<AccountAdminResponse>  getALl();
}
