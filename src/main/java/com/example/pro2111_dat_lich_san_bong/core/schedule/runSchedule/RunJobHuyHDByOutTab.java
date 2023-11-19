package com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule;

import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuyHoaDonOutTabService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl.JobHuySanCaServiceImpl;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author thepvph20110
 */
@Component
public class RunJobHuyHDByOutTab {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private JobHuyHoaDonOutTabService jobHuyHoaDonOutTabService;

    /**
     * Mô tả ngắn về chức năng của hàm hoặc phương thức.
     *
     * @param idHoaDon id của hoán đơn hết thời gian thanh toán.
     * @param timeOut thời gian hết thời gian thanh toán (phút)  .
     */
    public void khaiBaoInfoJobHuyHoaDon(String idHoaDon,int timeOut){
        logger.info("******* khai báo thông tin hủy hóa đơn khi đóng tab *********");
        try {
            LocalDateTime dateTimeNow = LocalDateTime.now();
            LocalDateTime updatedDateTime = dateTimeNow.plusMinutes(timeOut);
            int minute = updatedDateTime.getMinute();
            int hour = updatedDateTime.getHour();
            int day = updatedDateTime.getDayOfMonth();
            int month = updatedDateTime.getMonthValue();
            int year = updatedDateTime.getYear();
            String cronExpression = String.format("0 %d %d %d %d ?", minute, hour, day, month);

            jobHuyHoaDonOutTabService.createJobHuyHoaDonOutTab(cronExpression,idHoaDon);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("******* lỗi khai báo thông tin hủy hóa đơn khi đóng tab *********");
        }

    }
}
