package com.example.pro2111_dat_lich_san_bong;

import com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule.RunJobHuyLichSanCa;
import com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule.RunJobSendMailBeforeCa;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuySanCaService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class Pro2111DatLichSanBongApplication implements CommandLineRunner {

    @Autowired
    private RunJobHuyLichSanCa runJobHuyLichSanCa;

    @Autowired
    private RunJobSendMailBeforeCa runJobSendMailBeforeCa;

    public static void main(String[] args) {
        SpringApplication.run(Pro2111DatLichSanBongApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        //gọi hàm khai báo thông tin job hủy lịch sân ca khi quá giờ
        runJobHuyLichSanCa.khaiBaoInfoJob();

        //gọi hàm khai báo thông tin job gửi mail thông báo
        runJobSendMailBeforeCa.khaiBaoJobSendMail();
    }

}
