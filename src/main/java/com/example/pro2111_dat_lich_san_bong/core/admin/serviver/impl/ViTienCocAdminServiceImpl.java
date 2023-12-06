package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ViTienCocAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocNamReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ViTienCocAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ViTienCocAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class ViTienCocAdminServiceImpl implements ViTienCocAdminService {

    @Autowired
    private ViTienCocAdminRepository viTienCocAdminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ViTienCocAdminRespone> getAll(Pageable pageable) {
        try {
            Page<ViTienCoc> viTienCocs = viTienCocAdminRepository.findAll(pageable);
            TypeToken<Page<ViTienCocAdminRespone>> token = new TypeToken<Page<ViTienCocAdminRespone>>() {
            };
            return modelMapper.map(viTienCocs, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void create(ViTienCocAdminRequest viTienCocAdminRequest) {
        try {
            ViTienCoc viTienCoc = modelMapper.map(viTienCocAdminRequest, ViTienCoc.class);
            viTienCocAdminRepository.save(viTienCoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (viTienCocAdminRepository.existsById(id)) {
                viTienCocAdminRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ViTienCocAdminRequest viTienCocAdminRequest) {
        try {
            viTienCocAdminRepository.saveAndFlush(modelMapper.map(viTienCocAdminRequest, ViTienCoc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double tongTienThanhToanCocTheoHinhThucThanhToanNgay(Integer hinhThucThanhToan, LocalDate ngayTao) {
        try {
            Double tienMatCocNgay =  viTienCocAdminRepository.tongTienThanhToanCocTheoHinhThucThanhToanNgay(hinhThucThanhToan, ngayTao);
            if(tienMatCocNgay == null){
                return 0.0;
            }
            return tienMatCocNgay;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienThanhToanCocTheoHinhThucThanhToanTuan(Integer hinhThucThanhToan, Integer tuanTao) {
        try {
            Double tienMatCocTuan = viTienCocAdminRepository.tongTienThanhToanCocTheoHinhThucThanhToanTuan(hinhThucThanhToan, tuanTao);
            if(tienMatCocTuan == null){
                return 0.0;
            }
            return tienMatCocTuan;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }


    }

    @Override
    public Double tongTienThanhToanCocTheoHinhThucThanhToanThang(Integer hinhThucThanhToan, Integer thangTao, Integer namTao) {
        try {
            Double tienMatCocThang = viTienCocAdminRepository.tongTienThanhToanCocTheoHinhThucThanhToanThang(hinhThucThanhToan, thangTao, namTao);
            if(tienMatCocThang == null){
                return 0.0;
            }
            return tienMatCocThang;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienThanhToanCocTheoHinhThucThanhToanNam(Integer hinhThucThanhToan, Integer namTao) {
        try {
            Double tienMatCocNam = viTienCocAdminRepository.tongTienThanhToanCocTheoHinhThucThanhToanNam(hinhThucThanhToan, namTao);
            if(tienMatCocNam == null){
                return 0.0;
            }
            return tienMatCocNam;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienThanhToanCocTheoHinhThucThanhToanTrongCaLamViec(Integer hinhThucThanhToan, Timestamp thoiGianNhanCa) {
        try {
            Double tien = viTienCocAdminRepository.tongTienThanhToanCocTheoHinhThucThanhToanTrongCaLamViec(hinhThucThanhToan, thoiGianNhanCa);
            if(tien == null){
                return 0.0;
            }
            return tien;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public Double tongTienThanhToanCocTrongNgayTheoCa(Integer ngay, Integer thang, Integer nam, String timeStart, String timeEnd) {
        try {
            Double tien = viTienCocAdminRepository.tongTienThanhToanCocTrongNgayTheoCa(ngay,thang, nam, timeStart, timeEnd);
            if(tien == null){
                return 0.0;
            }
            return tien;
        }catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public List<ViTienCocNamReponse> findListTienCocNam(Integer year) {
        try {
            return viTienCocAdminRepository.findListTienCocNam(year);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
