package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;

import java.util.List;

/**
 * @author caodinh
 */
public interface IDatSanStaffService {

    List<LoadSanBongRespose> loadSanBong();

    List<LoadSanBongRespose> filterSanBong(FilterSanBongRequest filterSanBongRequest);

    boolean datLich(ThongTinNguoiDatRequest thongTinNguoiDatRequest);

    List<HoaDonStaffResponse> getHoaDonByTrangThai();

     void huySanBong(String idHoaDon);

}
