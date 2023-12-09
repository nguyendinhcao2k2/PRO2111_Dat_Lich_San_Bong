package com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobGuiMailthongBaoService;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class JobGuiMailThongBaoServiceImpl implements JobGuiMailthongBaoService {

    private static final Logger logger = LoggerFactory.getLogger(JobHuySanCaServiceImpl.class);

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private CaRepository caRepository;

    @Autowired
    private HoaDonSanCaReponsitory hoaDonSanCaReponsitory;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Override
    public void createJobSendMail(String cronExpression,String idCa){
        logger.info("---------- tạo job gửi thông báo trước thời gian bắt đầu ca với cron:{} ----------",cronExpression);
        Runnable job = () -> {

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                logger.info("---------- bắt đầu chạy job gửi mail thông báo trước thời gian bắt đầu ca --------");
                List<HoaDonSendMailResponse> hoaDonSanCaList = hoaDonSanCaReponsitory.listHoaDonSanCaByIdCaAndNow(idCa, TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                for (HoaDonSendMailResponse hoaDonSanCa : hoaDonSanCaList){
                    SendMailRequest sendMailRequest = new SendMailRequest();
                    sendMailRequest.setNguoiDat(hoaDonSanCa.getDisplayName());
                    sendMailRequest.setNguoiNhanMail(hoaDonSanCa.getEmail());
                    sendMailRequest.setGiaTien(hoaDonSanCa.getTienSan());
                    sendMailRequest.setTimeStart(hoaDonSanCa.getThoiGianBatDau());
                    sendMailRequest.setTimeEnd(hoaDonSanCa.getThoiGianKetThuc());
                    sendMailRequest.setQrCodeData(hoaDonSanCa.getMaQR());
                    String ngayDat = formatter.format(hoaDonSanCa.getNgayTao());
                    sendMailRequest.setNgayDat(ngayDat);
                    sendMailRequest.setNgayCheckIn(formatterNgayDa.format(hoaDonSanCa.getNgayDenSan()));
                    sendMailRequest.setTitle("Thông báo thời gian đá");

                    // thực hiện gửi mail
                    sendMailUtils.sendEmail(sendMailRequest);
                }

                logger.info("---------- kết thúc chạy gửi mail thông báo trước thời gian bắt đầu ca --------");
            }catch (Exception e){
                e.printStackTrace();
                logger.error("---------- lỗi khi chạy job gửi mail thông báo trước thời gian bắt đầu ca --------");
            }
        };
        taskScheduler.schedule(job,new CronTrigger(cronExpression));
    }
}
