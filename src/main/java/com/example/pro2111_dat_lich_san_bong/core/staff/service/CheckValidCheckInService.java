package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import java.sql.Time;
import java.time.LocalDate;

public interface CheckValidCheckInService {
    boolean checkNgayGioCheckIn(LocalDate ngayDenSan, Time thoiGianBatDau);
}
