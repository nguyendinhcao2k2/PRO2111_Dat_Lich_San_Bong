package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KetCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.GiaoCaStaffReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public GiaoCaResponse findGiaoCaByTrangThai(TrangThaiGiaoCa trangThaiGiaoCa) {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findGiaoCaByTrangThai(trangThaiGiaoCa);
            if (giaoCa != null) {
                GiaoCaResponse response = _modelMapper.map(giaoCa, GiaoCaResponse.class);
                return response;
            }
           return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GiaoCaResponse findFirstByOrderByThoiGianNhanCaDesc() {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findFirstByOrderByThoiGianNhanCaDesc();
            if (giaoCa != null) {
                GiaoCaResponse result = _modelMapper.map(giaoCa, GiaoCaResponse.class);
                return result;
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public GiaoCaResponse findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa trangThaiGiaoCa, String idAccount) {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findGiaoCaByTrangThaiAndIdAccount(trangThaiGiaoCa, idAccount);
            if (giaoCa != null) {
                GiaoCaResponse giaoCaResponse = _modelMapper.map(giaoCa, GiaoCaResponse.class);
                return giaoCaResponse;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer tongSoHoaDonTrongCaFollowStatus(Integer trangThaiGC, Integer trangThaiHDSC) {
        try {
            Integer soHoaDon = _giaoCaStaffReponsitory.tongSoHoaDonTrongCaFollowStatus(trangThaiGC, trangThaiHDSC);
            return soHoaDon;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public Double tongTienTrongCaTheoHinhThucThanhToan(Integer loaiHinhThanhToan) {
        try {
            Double tongTien = _giaoCaStaffReponsitory.tongTienTrongCaTheoHinhThucThanhToan(loaiHinhThanhToan);
            return tongTien;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean ketCa(KetCaRequest ketCaRequest) {
        try {
            GiaoCa giaoCa = _giaoCaStaffReponsitory.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);
            giaoCa.setIdNhanVienCaTiepTheo(ketCaRequest.getIdNhanVienCaTiepTheo());
            giaoCa.setGhiChuPhatSinh(ketCaRequest.getGhiChuPhatSinh());
            giaoCa.setThoiGianKetCa(ketCaRequest.getThoiGianKetCa());
            giaoCa.setTienPhatSinh(ketCaRequest.getTienPhatSinh());
            giaoCa.setTongTienKhac(ketCaRequest.getTongTienKhac());
            giaoCa.setTongTienMat(ketCaRequest.getTongTienMat());
            giaoCa.setTongTienTrongCa(ketCaRequest.getTongTienTrongCa());
            giaoCa.setTongTienMatRut(ketCaRequest.getTongTienMatRut());
            giaoCa.setThoiGianReset(ketCaRequest.getThoiGianReset());
            giaoCa.setTrangThai(TrangThaiGiaoCa.KET_THUC_CA);
            _giaoCaStaffReponsitory.saveAndFlush(giaoCa);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<GiaoCaResponse> giaoCaList() {
        try {
            List<GiaoCa> giaoCaList = _giaoCaStaffReponsitory.findAll();
            if (giaoCaList.size() == 0) {
                return null;
            }
            TypeToken<List<GiaoCaResponse>> token = new TypeToken<List<GiaoCaResponse>>() {
            };
            return _modelMapper.map(giaoCaList, token.getType());
        } catch (Exception e) {
            return null;
        }
    }


}
