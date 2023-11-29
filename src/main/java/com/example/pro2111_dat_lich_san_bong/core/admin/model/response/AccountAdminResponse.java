package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thepvph20110
 */
@Projection(types = {Account.class, ChucVu.class})
public interface AccountAdminResponse {

    @Value("#{target.taiKhoan}")
    String getTaiKhoan();

    @Value("#{target.matKhau}")
    String getMatKhau();

    @Value("#{target.tenChucVu}")
    String getTenChucVu();
}
