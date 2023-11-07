package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.SanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.ThoiGianDLUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiThoiGianDL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class SanCaUserServiceImpl implements SanCaUserService {

    @Autowired
    private SanCaUserRepository sanCaUserRepository;

    @Autowired
    private ThoiGianDLUserRepository thoiGianDLUserRepository;


    @Override
    public List getAllSanCa(EventRequest request) {
        List<SanCaUserResponse> listSanCa = sanCaUserRepository.getAllSanCa(request);
        List<SanCaUserResponse> listTGDatLich = thoiGianDLUserRepository.getAllTGDLByUserId(request, TrangThaiThoiGianDL.CHO_XAC_NHAN.ordinal());
        if(listTGDatLich != null || !listTGDatLich.isEmpty()){
            listSanCa.addAll(listTGDatLich);
        }
        return listSanCa;
    }
}
