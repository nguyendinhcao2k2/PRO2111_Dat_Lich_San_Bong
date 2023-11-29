package com.example.pro2111_dat_lich_san_bong.core.common.session;

import com.example.pro2111_dat_lich_san_bong.entity.Account;

/**
 * @author thepvph20110
 */
public interface CommonSession {

    String getUserId();

    String getRole();

    Account getEntityAcount();
}
