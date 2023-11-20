package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IThanhToanSanCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanSanCaStaffServiceImpl implements IThanhToanSanCaStaffService {
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private SYSParamUserService sysParamUserService;

    @Override
    public List<HoaDonThanhToanRequest> getAllHoaDonSanCas() {
        List<HoaDonThanhToanRequest> listhoaDonThanhToanRequest = hoaDonSanCaStaffRepository.findAllByTrangThai(TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal(), TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal());
        for (HoaDonThanhToanRequest hoaDonThanhToanRequest : listhoaDonThanhToanRequest) {
            Double tongTienCoc = hoaDonThanhToanRequest.getTongTienSanCa();
            hoaDonThanhToanRequest.setTienCoc(sysParamUserService.getTienCoc(tongTienCoc));
        }
        return listhoaDonThanhToanRequest;
    }

    @Override
    public HoaDonThanhToanRequest getOneHoaDonThanhToan(String id) {
        HoaDonThanhToanRequest hoaDonThanhToanRequest = hoaDonSanCaStaffRepository.findOneById(id);
        hoaDonThanhToanRequest.setTienCoc(sysParamUserService.getTienCoc(hoaDonThanhToanRequest.getTongTienSanCa()));
        return hoaDonThanhToanRequest;
    }
}
