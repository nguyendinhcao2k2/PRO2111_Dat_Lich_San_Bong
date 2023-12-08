package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DoThueNuocUongDichVuRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuDichVuSuDungStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DoThueStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.NuocUongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DichVuSanBongStaffServiceImpl implements IDichVuSanBongStaffService {
    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;

    @Autowired
    private DoThueStaffRepository doThueStaffRepository;
    @Autowired
    private NuocUongStaffRepository nuocUongStaffRepository;

    @Override
    public List<DichVuSanBongRequest> dichVuSanBongSuDungByHoaDonSanCas(String idHoaDonSanCa, int trangThai) {
        return dichVuSanBongStaffRepository.dichVuSanBongSuDungByHoaDonSanCas(idHoaDonSanCa, trangThai);
    }

    @Override
    public List<DichVuSanBong> findAllByIdHoaDonSanCaAndTrangThai(String idHoaDonSanCa, int trangThai) {
        return dichVuSanBongStaffRepository.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, trangThai);
    }

    @Override
    public List<DoThueNuocUongDichVuRequest> getAllDoThueNuocUongDichVuRequest(int trangThai) {
        List<DoThue> listDoThues = doThueStaffRepository.getAllByTrangThai(trangThai);
        List<NuocUong> listNuocUongs = nuocUongStaffRepository.getAllByTrangThai(trangThai);
        List<DoThueNuocUongDichVuRequest> listDoThueNuocUongDichVuRequests = new ArrayList<>();

        int nuocUongTrangThai = 1;
        int doThueTrangThai = 2;

        for (NuocUong nuocUong : listNuocUongs) {
            if (nuocUong.getTrangThai() == 0 && nuocUong.getSoLuong() > 0) {
                DoThueNuocUongDichVuRequest request = new DoThueNuocUongDichVuRequest();
                request.setId(nuocUong.getId());
                request.setImage(nuocUong.getImage());
                request.setTenDichVu(nuocUong.getTenNuocUong());
                request.setSoLuong(nuocUong.getSoLuong());
                request.setDonGia(nuocUong.getDonGia());
                request.setTrangThai(nuocUongTrangThai);
                listDoThueNuocUongDichVuRequests.add(request);
            }
        }

        for (DoThue doThue : listDoThues) {
            if (doThue.getTrangThai() == 0 && doThue.getSoLuong() > 0) {
                DoThueNuocUongDichVuRequest request = new DoThueNuocUongDichVuRequest();
                request.setId(doThue.getId());
                request.setImage(doThue.getImage());
                request.setTenDichVu(doThue.getTenDoThue());
                request.setSoLuong(doThue.getSoLuong());
                request.setDonGia(doThue.getDonGia());
                request.setTrangThai(doThueTrangThai);
                listDoThueNuocUongDichVuRequests.add(request);
            }
        }
        System.out.println(listDoThueNuocUongDichVuRequests.size());
        return listDoThueNuocUongDichVuRequests;
    }

    @Override
    public List<DichVuSanBong> findAllByLichSuSuDungDichVu(String idHoaDonSanCa) {
        try {
            return dichVuSanBongStaffRepository.findAllByLichSuSuDungDichVu(idHoaDonSanCa);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
