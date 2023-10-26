package com.example.pro2111_dat_lich_san_bong.core.common.session;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SessionConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author thepvph20110
 */

@Component
 public class CommonSessionImpl implements CommonSession{

    @Autowired
    private HttpSession session;

    @Override
    public String getUserId() {
        Account account = (Account) session.getAttribute(SessionConstant.sessionUser);
        return account.getId();
    }

    @Override
    public String getRole() {
        ChucVu chucVu = (ChucVu) session.getAttribute(SessionConstant.sessionRole);
        return chucVu.getTenChucVu();
    }

    @Override
    public Account getEntityAcount() {
        return (Account) session.getAttribute(SessionConstant.sessionUser);
    }
}
