package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author thepvph20110
 */
public interface ThoiGianDLUserServiver {

     void saveThoiGianDatLich(ThoiGianDatLich thoiGianDatLich);

     void saveAllThoiGianDatLich(List list);

     ThoiGianDatLich findTGDLByIdCaAndIdLsAndNgayDa(String idHD, Date ngayDat, String idCA, String idLS);
}
