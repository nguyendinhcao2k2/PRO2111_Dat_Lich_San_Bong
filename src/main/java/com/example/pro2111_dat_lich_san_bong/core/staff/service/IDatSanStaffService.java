package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author caodinh
 */
public interface IDatSanStaffService {

    List<LoadSanBongRespose> loadSanBong();

    List<LoadSanBongRespose> filterSanBong(FilterSanBongRequest filterSanBongRequest);

    boolean datLich(ThongTinNguoiDatRequest thongTinNguoiDatRequest,HttpServletRequest request);

    List<HoaDonStaffResponse> getHoaDonByTrangThai();

     void huySanBong(String idHoaDon);

    List<HoaDonStaffResponse> filterHoaDon(FilterLichDatRequest filterSanBongRequest);

    void sendMailDatLich(HttpServletRequest request, String idhoaDon);

}
