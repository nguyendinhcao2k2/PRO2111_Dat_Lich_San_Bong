package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.excel.GiaoCaExportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.GiaoCaAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.GiaoCaAdminService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GiaoCaAdminServiceImpl implements GiaoCaAdminService {

    @Autowired
    private GiaoCaAdminRepository giaoCaAdminRepository;

    @Override
    public Page<QuanLyGiaoCaResponse> findAllGiaoCaByStatus(Pageable pageable, TrangThaiGiaoCa status) {
        try {
            Page<QuanLyGiaoCaResponse> findAll = giaoCaAdminRepository.findAllGiaoCaByStatus(pageable, status);
            return findAll;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<QuanLyGiaoCaResponse> searchByName(Pageable pageable, String name) {
        try {
            return giaoCaAdminRepository.searchByName(pageable, name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<QuanLyGiaoCaResponse> giaoCaCoTienRut(Pageable pageable) {
        try {
            return giaoCaAdminRepository.giaoCaCoTienRut(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<QuanLyGiaoCaResponse> giaoCaByThoiGianNhanCa(Pageable pageable, Date time) {
        try {
            return giaoCaAdminRepository.giaoCaByThoiGianNhanCa(pageable, time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public QuanLyGiaoCaResponse findGiaoCaByIdAndStatus(TrangThaiGiaoCa trangThaiGiaoCa, String id) {
        try {
            return giaoCaAdminRepository.findGiaoCaByIdAndStatus(trangThaiGiaoCa, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<QuanLyGiaoCaResponse> findAllGiaoCaAndOrderByTimeNhanCa() {
        try {
            return giaoCaAdminRepository.findAllGiaoCaAndOrderByTimeNhanCa();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void exportExcel(HttpServletResponse response, List<QuanLyGiaoCaResponse> list) {
        try {
            GiaoCaExportExcel.exportData(response, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
