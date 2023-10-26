package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseIdTemplate;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thepvph20110
 */

@Projection(types = {SanCa.class, SanBong.class, Ca.class})
public interface SanCaTrangChuResponse extends BaseIdTemplate {

}
