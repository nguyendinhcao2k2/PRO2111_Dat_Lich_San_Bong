package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongTinAccountThongKeReponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountThongKeService {
    List<ThongTinAccountThongKeReponse> getAllAccountByChucVu(String tenChucVu);
}
