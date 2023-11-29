package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanBongUserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface SanBongUserService {

    PageableObject<SanBongUserResponse> getAllSanBong(Pageable page);

    Integer countSanBong(String idLoaiSan);

    List getALlSanBongByIdLoaiSan(String idLoaiSan);
}
