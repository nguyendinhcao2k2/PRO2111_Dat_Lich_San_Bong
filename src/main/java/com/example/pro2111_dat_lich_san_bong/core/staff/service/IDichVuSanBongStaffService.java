package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DoThueNuocUongDichVuRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuDichVuSuDungStaffReponse;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDichVuSanBongStaffService {
    List<DichVuSanBongRequest> dichVuSanBongSuDungByHoaDonSanCas(String idHoaDonSanCa, int trangThai);

    List<DichVuSanBong> findAllByIdHoaDonSanCaAndTrangThai(String idHoaDonSanCa, int trangThai);

    List<DoThueNuocUongDichVuRequest> getAllDoThueNuocUongDichVuRequest(int trangThai);

    List<DichVuSanBong> findAllByLichSuSuDungDichVu( String idHoaDonSanCa);

}
