package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse;

import java.util.List;

public interface ThongKeAdminService {
    List<ThongKeTheoNamAdminResponse> sumTongTienByMonthAndYear(Integer year);

    Double thongKeTheoCaTrongNgay(Integer ngay, Integer thang, Integer nam, String timeStart, String timeEnd);

    List findThongKeDoThueTheoNam(Integer year);

    List findThongKeDoThueTheoThangTrongNam(Integer year, Integer month);

    List findThongKeDoThueTheoNgay(Integer year, Integer month, Integer day);

    //nuoc uong
    List findThongKeNuocUongTheoNam(Integer year);

    List findThongKeNuocUongTheoThangTrongNam(Integer year, Integer month);

    List findThongKeNuocUongTheoNgay(Integer year, Integer month, Integer day);
}
