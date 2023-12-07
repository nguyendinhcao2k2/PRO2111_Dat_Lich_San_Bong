package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.CheckValidCheckInService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class CheckValidCheckInServiceImpl implements CheckValidCheckInService {

    private LocalDate ngayHienTai = LocalDate.now();
    private LocalTime gioHienTai = LocalTime.now();

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Override
    public boolean checkNgayGioCheckIn(LocalDate ngayDenSan, Time thoiGianBatDau) {
        try {
            LocalDateTime ngayGioHienTai = LocalDateTime.now();
            LocalDateTime ngayGioDenSan = ngayDenSan.atTime(thoiGianBatDau.toLocalTime());
//            Thời gian giữa 2 ngày
            Duration khoangTHoiGian = Duration.between(ngayGioDenSan,ngayGioHienTai);
//            số phút
            long soPhut = Math.abs(khoangTHoiGian.toMinutes());
            System.out.println("HelloA:"+soPhut);

//            if (!ngayHienTai.isEqual(ngayDenSan)) {
//                return false;
//            }
//            LocalTime gioBatDauLocalTime = thoiGianBatDau.toLocalTime();
//            long soPhutGiuaHaiGio = Math.abs(TimeUnit.HOURS.toMinutes(gioHienTai.until(gioBatDauLocalTime, ChronoUnit.HOURS)));
            Integer thoiGianChoPhepCheckIn = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_DUOC_PHEP_CHECK_IN).getValue());
            if (soPhut > thoiGianChoPhepCheckIn) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
