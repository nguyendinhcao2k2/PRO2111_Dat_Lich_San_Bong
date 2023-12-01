package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author caodinh
 */
@Projection(types = {DoThue.class})
public interface DoThueResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.donGia}")
    Double getDonGia();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

    @Value("#{target.tenDoThue}")
    String getTenDoThue();

    @Value("#{target.image}")
    String getImage();

    @Value("#{target.trangThai}")
    String getTrangThai();
}
