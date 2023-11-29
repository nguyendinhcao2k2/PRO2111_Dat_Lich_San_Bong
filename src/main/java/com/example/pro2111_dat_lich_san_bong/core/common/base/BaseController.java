package com.example.pro2111_dat_lich_san_bong.core.common.base;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author thepvph20110
 */
public abstract class BaseController {

    @Autowired
    protected CommonSession session;

}
