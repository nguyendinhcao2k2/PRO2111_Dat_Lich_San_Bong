package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThongKeAdminService {
    List<ThongKeTheoNamAdminResponse> sumTongTienByMonthAndYear(Integer year);

    Double thongKeTheoCaTrongNgay(Integer ngay, Integer thang, Integer nam, String timeStart, String timeEnd);
}
