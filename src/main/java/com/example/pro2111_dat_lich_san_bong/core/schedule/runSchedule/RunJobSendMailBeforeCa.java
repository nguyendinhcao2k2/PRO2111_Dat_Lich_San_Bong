package com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule;

import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobGuiMailthongBaoService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl.JobHuySanCaServiceImpl;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
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
public class RunJobSendMailBeforeCa {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private JobGuiMailthongBaoService jobGuiMailthongBaoService;

    @Autowired
    private CaRepository caRepository;

    @Autowired
    private SYSParamUserService sysParamUserService;

    public void khaiBaoJobSendMail(){
        try {
            logger.info("********** bắt đầu khai báo thông tin job gửi mail thông báo **********");
            List<Ca> listCa = caRepository.findAll();

            SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_THONG_BAO);
            Long thoiGianGui= param.getValue() == null ||param.getValue()=="" ? 30: Long.valueOf(param.getValue());
            for (Ca ca: listCa) {
                String idCa = ca.getId();
                Time thoiGianKTCa = ca.getThoiGianBatDau();
                LocalTime localTime = thoiGianKTCa.toLocalTime();

                // cổng thêm thời gian param gửi mail
                LocalTime plusTime = localTime.minusMinutes(thoiGianGui);
                int gio = plusTime.getHour();
                int phut = plusTime.getMinute();
                int giay = plusTime.getSecond();
                // Tạo biểu thức cron
                String cronExpression = String.format("%d %d %d * * *", giay, phut, gio);
                jobGuiMailthongBaoService.createJobSendMail(cronExpression,idCa);
            }
            logger.info("********** kết thúc khai báo thông tin job gửi mail thông báo **********");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("----------- Lỗi khi thực hiện khai báo thông tin job gửi mail thông báo ------------");
        }
    }
}
