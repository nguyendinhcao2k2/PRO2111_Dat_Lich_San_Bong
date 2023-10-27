package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;

import java.util.List;

/**
 * @author caodinh
 */
public interface IDatSanStaffService {

    List<LoadSanBongRespose> loadSanBong(String date);

    boolean datLich(ThongTinNguoiDatRequest thongTinNguoiDatRequest);

}
