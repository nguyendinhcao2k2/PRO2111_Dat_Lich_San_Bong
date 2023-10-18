package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.GiaoCaStaffReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiaoCaStaffServiceImpl implements IGiaoCaStaffService {

    @Autowired
    private GiaoCaStaffReponsitory _giaoCaStaffReponsitory;

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private IAccountStaffService _accountStaffService;

    @Autowired
    private ModelMapper _modelMapper;

    @Override
    public Boolean khoiTaoCaLamViec(KhoiTaoCaRequest khoiTaoCaRequest) {
        try {
            String idAccount = commonSession.getUserId();
            if (idAccount != null) {
                AccountResponse accountResponse = _accountStaffService.findAccountById(idAccount);
                khoiTaoCaRequest.setIdAccount(idAccount);
                khoiTaoCaRequest.setIdNhanVienTrongCa(accountResponse.getId());
                GiaoCa giaoCa = _modelMapper.map(khoiTaoCaRequest, GiaoCa.class);
                _giaoCaStaffReponsitory.save(giaoCa);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GiaoCaResponse findGiaoCaByTrangThai(Integer trangThaiGiaoCa) {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findGiaoCaByTrangThai(trangThaiGiaoCa);
            GiaoCaResponse response = _modelMapper.map(giaoCa, GiaoCaResponse.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GiaoCaResponse findFirstByOrderByThoiGianNhanCaDesc() {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findFirstByOrderByThoiGianNhanCaDesc();
            GiaoCaResponse result = _modelMapper.map(giaoCa, GiaoCaResponse.class);
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public GiaoCaResponse findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount) {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findGiaoCaByTrangThaiAndIdAccount(trangThaiGiaoCa, idAccount);
            GiaoCaResponse giaoCaResponse = _modelMapper.map(giaoCa, GiaoCaResponse.class);
            return giaoCaResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
