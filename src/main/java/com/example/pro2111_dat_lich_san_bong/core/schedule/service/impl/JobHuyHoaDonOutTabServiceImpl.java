package com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuyHoaDonOutTabService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuySanCaService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class JobHuyHoaDonOutTabServiceImpl implements JobHuyHoaDonOutTabService {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Override
    public void createJobHuyHoaDonOutTab(String cronExpression,String idHoaDon){
        logger.info("---------- tạo job hủy lịch khi trong quá trình thanh toán tắt tab với cron:{} ----------",cronExpression);
        Runnable job = () -> {
            try {
                logger.info("---------- bắt đầu chạy job hủy lịch khi tắt tab --------");

                HoaDon hoaDon = hoaDonUserService.findHoaDonById(idHoaDon);
                if(hoaDon != null && hoaDon.getTrangThai() == TrangThaiHoaDon.MOI_TAO.ordinal()){
                    //lấy tất cả hóa đơn sân ca có idHoaDon
                    List<HoaDonSanCa> list = hoaDonSanCaUserService.findAllByIdHoaDon(idHoaDon);
                    for (HoaDonSanCa hoaDonSanCa: list
                    ) {
                        String idSanCa = hoaDonSanCa.getIdSanCa();
                        sanCaUserService.deleteSanCaById(idSanCa);
                    }
                    hoaDonSanCaUserService.deleteAllByIdHoaDon(idHoaDon);
                    hoaDonUserService.deleteHoaDonById(idHoaDon);
                }

                logger.info("---------- kết thúc chạy job hủy lịch khi tắt tab --------");
            }catch (Exception e){
                logger.error("---------- lỗi khi chạy job hủy lịch khi tắt tab --------");
            }
        };
        taskScheduler.schedule(job,new CronTrigger(cronExpression));
    }
}
