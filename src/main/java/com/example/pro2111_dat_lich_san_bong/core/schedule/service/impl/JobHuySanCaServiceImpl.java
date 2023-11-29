package com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.request.SanCaJobRequest;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuySanCaService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;



/**
 * @author thepvph20110
 */


@Service
public class JobHuySanCaServiceImpl implements JobHuySanCaService {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private SanCaRepository sanCaRepository;

    @Autowired
    private HoaDonSanCaReponsitory hoaDonSanCaReponsitory;

    @Override
    public void CreateJobHuyLichSanCa(String cronExpression,String idCa) {
        //job này thực hiện hủy tất cả sân ca của ngày hôm nay theo idCa
        logger.info("-------------------- thực hiện tạo job hủy lịch với cron= {} ----------------",cronExpression);
        Runnable job = () -> {
            try {
                logger.info("---------- bắt đầu chạy job hủy lịch đặt kết ca ---------------");

                //lấy nhày hiện tại
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateNow = currentDate.format(formatter);

                SanCaJobRequest request = new SanCaJobRequest();
                request.setNgayTao(dateNow);
                request.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal());
                request.setIdCa(idCa);

                //lấy tất cả sân ca của ngày hôm nay và id ca có trạng thái chờ nhận để hủy
                List<SanCa> listSanCa = (List<SanCa>) sanCaRepository.findAllSanCaByIdCa(request);
                for (SanCa sanCa: listSanCa) {
                    String idSanCa = sanCa.getId();
                    HoaDonSanCa hoaDonSanCa = hoaDonSanCaReponsitory.findHoaDonSanCaByIdSanCa(idSanCa);
                    if(hoaDonSanCa != null && Objects.nonNull(hoaDonSanCa)){
                        //hủy hoa_don san ca nếu không đến sân sau thời gian kết thúc ca
                        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_HUY.ordinal());
                        hoaDonSanCaReponsitory.saveAndFlush(hoaDonSanCa);
                    }
                    //đổi trạng thái sân ca từ chờ nhận sân -> quá giờ vì không đến sân
                    sanCa.setTrangThai(TrangThaiSanCa.HUY_QUA_GIO.ordinal());
                    sanCaRepository.saveAndFlush(sanCa);
                }

                logger.info("---------- kết thúc chạy job hủy lịch đặt kết ca -----------");
            }catch (Exception e){
                logger.error("------------ lỗi chạy job hủy lịch đặt kết ca --------------");
            }

        };

        taskScheduler.schedule(job, new CronTrigger(cronExpression));
    }
}
