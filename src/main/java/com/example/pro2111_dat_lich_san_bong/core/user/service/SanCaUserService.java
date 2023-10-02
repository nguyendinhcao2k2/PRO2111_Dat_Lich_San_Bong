package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface SanCaUserService {

    List getAllSanCa(SanCaUserRequest request);
}
