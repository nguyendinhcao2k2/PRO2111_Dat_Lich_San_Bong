package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanCaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.ViTienCocAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanCaAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ViTienCocNamReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface ViTienCocAdminService {
    Page<ViTienCocAdminRespone> getAll(Pageable pageable);

    void create(ViTienCocAdminRequest viTienCocAdminRequest);

    void delete(String id);

    void update( ViTienCocAdminRequest viTienCocAdminRequest);

    Double tongTienThanhToanCocTheoHinhThucThanhToanNgay(Integer hinhThucThanhToan, LocalDate ngayTao);
    Double tongTienThanhToanCocTheoHinhThucThanhToanTuan(Integer hinhThucThanhToan,Integer tuanTao);
    Double tongTienThanhToanCocTheoHinhThucThanhToanThang(Integer hinhThucThanhToan,Integer thangTao,Integer namTao);
    Double tongTienThanhToanCocTheoHinhThucThanhToanNam(Integer hinhThucThanhToan, Integer namTao);
    Double tongTienThanhToanCocTheoHinhThucThanhToanTrongCaLamViec(Integer hinhThucThanhToan, Timestamp thoiGianNhanCa);
    Double tongTienThanhToanCocTrongNgayTheoCa(Integer ngay, Integer thang, Integer nam, String timeStart, String timeEnd);
    List<ViTienCocNamReponse> findListTienCocNam(Integer year);

}
