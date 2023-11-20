package com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule;

import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuySanCaService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl.JobHuySanCaServiceImpl;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * @author thepvph20110
 */
@Component
public class RunJobHuyLichSanCa {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private JobHuySanCaService jobHuySanCaService;

    @Autowired
    private CaRepository caRepository;

    public void khaiBaoInfoJob(){
        try {
            logger.info("********** bắt đầu khai báo thông tin job hủy lịch **********");
            List<Ca> listCa = caRepository.findAll();
            for (Ca ca: listCa) {
                String idCa = ca.getId();
                Time thoiGianKTCa = ca.getThoiGianKetThuc();
                LocalTime localTime = thoiGianKTCa.toLocalTime();
                int gio = localTime.getHour();
                int phut = localTime.getMinute();
                int giay = localTime.getSecond();
                // Tạo biểu thức cron
                String cronExpression = String.format("%d %d %d * * *", giay, phut, gio);
                jobHuySanCaService.CreateJobHuyLichSanCa(cronExpression,idCa);
            }
            logger.info("********** kết thúc khai báo thông tin job hủy lịch **********");
        }catch (Exception e){
            logger.error("----------- Lỗi khi thực hiện khai báo thông tin job hủy lịch ------------");
        }

    }
}
