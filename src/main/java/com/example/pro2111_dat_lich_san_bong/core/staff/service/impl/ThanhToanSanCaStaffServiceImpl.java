package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.ViTienStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IThanhToanSanCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuViTien;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
import com.example.pro2111_dat_lich_san_bong.repository.LichSuViTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThanhToanSanCaStaffServiceImpl implements IThanhToanSanCaStaffService {
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private SYSParamUserService sysParamUserService;
    @Autowired
    private ViTienStaffRepository viTienStaffRepository;
    @Autowired
    private LichSuViTienRepository lichSuViTienRepository;

    private void processHoaDonList(List<HoaDonThanhToanRequest> list) {
        list.forEach(this::processHoaDonThanhToanRequest);
    }

    @Override
    public List<HoaDonThanhToanRequest> getAllHoaDonSanCas() {
        List<HoaDonThanhToanRequest> listHoaDonThanhToanRequest = hoaDonSanCaStaffRepository.findAllByTrangThai(
                TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal(),
                TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal()
        );
        processHoaDonList(listHoaDonThanhToanRequest);
        return listHoaDonThanhToanRequest;
    }

    @Override
    public List<HoaDonThanhToanRequest> getAllHoaDonSanCaByPhone(String numberPhone) {
        List<HoaDonThanhToanRequest> listHoaDonThanhToanRequest = hoaDonSanCaStaffRepository.findAllBySoDienThoai(numberPhone,
                TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal(),
                TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal());
        processHoaDonList(listHoaDonThanhToanRequest);
        return listHoaDonThanhToanRequest;
    }

    @Override
    public HoaDonThanhToanRequest getOneHoaDonThanhToan(String id) {
        HoaDonThanhToanRequest hoaDonThanhToanRequest = hoaDonSanCaStaffRepository.findOneById(id);
        if (hoaDonThanhToanRequest != null) {
            hoaDonThanhToanRequest.setTienCoc(sysParamUserService.getTienCoc(hoaDonThanhToanRequest.getTongTienSanCa()));
            if (hoaDonThanhToanRequest.getTienCocThua() == null) {
                hoaDonThanhToanRequest.setTienCocThua(Double.valueOf(0));
            }
            return hoaDonThanhToanRequest;
        }
        return null;
    }

    private void processHoaDonThanhToanRequest(HoaDonThanhToanRequest request) {
        Double tongTienCoc = request.getTongTienSanCa();
        request.setTienCoc(sysParamUserService.getTienCoc(tongTienCoc));
        if (request.getTienCocThua() == null) {
            request.setTienCocThua(Double.valueOf(0));
        }
    }

    public void saveViTienCocAndLichSu(HoaDonSanCa hoaDonSanCa) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        HoaDonThanhToanRequest hoaDonThanhToanRequest = getOneHoaDonThanhToan(hoaDonSanCa.getId());
        if (hoaDonThanhToanRequest != null) {
            Double tienCoc = hoaDonThanhToanRequest.getTienCoc();
            ViTienCoc viTienCoc = viTienStaffRepository.getViTienCocByIdHoaDon(hoaDonSanCa.getIdHoaDon());
            if (viTienCoc != null) {
                if (viTienCoc.getSoTien() != null) {
                    LichSuViTien lichSuViTien = new LichSuViTien();
                    lichSuViTien.setSoTien(tienCoc);
                    lichSuViTien.setIdViTienCoc(viTienCoc.getId());
                    lichSuViTien.setThoiGian(currentDateTime);
                    lichSuViTien.setLoaiBienDong(TrangThaiLoaiBienDong.TRU_TIEN);
                    lichSuViTien.setTaiKhoanVi(viTienCoc.getId());
                    lichSuViTien.setNguoiNhan(hoaDonSanCa.getId());
                    lichSuViTienRepository.saveAndFlush(lichSuViTien);
                } else {
                    System.out.println("LỖI Ở THANH TOÁN SÂN CA SERVICE:");
                }
            }
        }
    }
}
