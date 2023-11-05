package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface ThoiGianDLUserServiver {

     void saveThoiGianDatLich(ThoiGianDatLich thoiGianDatLich);

     void saveAllThoiGianDatLich(List list);
}
