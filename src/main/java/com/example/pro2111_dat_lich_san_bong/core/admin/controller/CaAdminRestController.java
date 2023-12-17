package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.excel.CaExportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.excel.CaImportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CheckTimeListReQuest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.CaAdminReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.CaAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule.RunJobHuyLichSanCa;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobGuiMailthongBaoService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.JobHuySanCaService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.service.impl.JobHuySanCaServiceImpl;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.SysParam;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/ca")
public class CaAdminRestController {

    private static final Logger logger = LoggerFactory.getLogger(CaAdminRestController.class);

    @Autowired
    private CaAdminService caAdminService;

    @Autowired
    private RunJobHuyLichSanCa runJobHuyLichSanCa;

    @Autowired
    private JobHuySanCaService jobHuySanCaService;

    @Autowired
    private JobGuiMailthongBaoService jobGuiMailthongBaoService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @GetMapping("find-all")
    public BaseResponse<?> getAllCa(@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page) {
        try {
            Page<CaAdminReponse> caAdminReponses = caAdminService.findAllCa(size.orElse(10), page.orElse(0));
            PageableObject<CaAdminReponse> pageableObject = new PageableObject<>(caAdminReponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("search")
    public BaseResponse<?> searchByName(@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page, @RequestParam("name") String tenCa) {
        try {
            Page<CaAdminReponse> caAdminReponses = caAdminService.findByName(size.orElse(10), page.orElse(0), tenCa);
            PageableObject<CaAdminReponse> pageableObject = new PageableObject<>(caAdminReponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("find/{id}")
    public BaseResponse<?> findById(@PathVariable("id") String id) {
        try {
            Ca ca = caAdminService.findById(id);
            return new BaseResponse<>(HttpStatus.OK, ca);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid");
        }
    }

    @PostMapping("create")
    public BaseResponse<?> createCa(@RequestBody CaAdminRequest caAdminRequest) {
        try {
            boolean check = checkThoiGianCa(caAdminRequest.getThoiGianBatDau(), caAdminRequest.getThoiGianKetThuc());
            if (!check) {
                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
            }

            Ca ca= caAdminService.saveOrUpdate(caAdminRequest);

            try {
                logger.info("********** bắt đầu khai báo thông tin job hủy lịch **********");

                String idCa = ca.getId();
                Time thoiGianKTCa = ca.getThoiGianKetThuc();
                LocalTime localTime = thoiGianKTCa.toLocalTime();
                int gio = localTime.getHour();
                int phut = localTime.getMinute();
                int giay = localTime.getSecond();
                // Tạo biểu thức cron
                String cronExpression = String.format("%d %d %d * * *", giay, phut, gio);
                jobHuySanCaService.CreateJobHuyLichSanCa(cronExpression,idCa);

                logger.info("********** kết thúc khai báo thông tin job hủy lịch **********");


                logger.info("********** bắt đầu khai báo thông tin job gửi mail thông báo **********");

                SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_THONG_BAO);
                Long thoiGianGui= param.getValue() == null ||param.getValue()=="" ? 30: Long.valueOf(param.getValue());

                    Time thoiGianBDCa = ca.getThoiGianBatDau();
                    LocalTime localTimeMail = thoiGianBDCa.toLocalTime();

                    // cổng thêm thời gian param gửi mail
                    LocalTime plusTime = localTimeMail.minusMinutes(thoiGianGui);
                    int gioMail = plusTime.getHour();
                    int phutMail = plusTime.getMinute();
                    int giayMail = plusTime.getSecond();
                    // Tạo biểu thức cron
                    String cronExpressionMail = String.format("%d %d %d * * *", giayMail, phutMail, gioMail);
                    jobGuiMailthongBaoService.createJobSendMail(cronExpressionMail,idCa);

                logger.info("********** kết thúc khai báo thông tin job gửi mail thông báo **********");
            }catch (Exception e){
                logger.error("----------- Lỗi khi thực hiện khai báo thông tin job hủy lịch ------------");
                logger.error("----------- Lỗi khi thực hiện khai báo thông tin job gửi mail ------------");
            }


            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    private boolean checkThoiGianCa(Time thoiGianBatDauNew, Time thoiGianKetThucNew) {
        List<Ca> caList = caAdminService.findAllListCa();
        for (Ca ca : caList) {
            //neu thoi gian bat dau nam trong khoang thoi gian ca nào đó thì là false
            if (thoiGianBatDauNew.after(ca.getThoiGianBatDau()) && thoiGianBatDauNew.before(ca.getThoiGianKetThuc())) {
                return false;
            }
            //neu thoi gian ket thuc nam trong khoang thoi gian ca nào đó thì là false
            if (thoiGianKetThucNew.after(ca.getThoiGianBatDau()) && thoiGianKetThucNew.before(ca.getThoiGianKetThuc())) {
                return false;
            }
            //neu thoi gian bat dau be hon thoi gian bat dau cu và thoi gian ket thuc lơn hon thoi gian ket thuc cu => false
            if (thoiGianBatDauNew.before(ca.getThoiGianBatDau()) && thoiGianKetThucNew.after(ca.getThoiGianKetThuc())) {
                return false;
            }
        }
        return true;
    }

    @PutMapping("update")
    public BaseResponse<?> updateCa(@RequestBody CaAdminRequest caAdminRequest) {
        try {
            Ca caCu = caAdminService.findById(caAdminRequest.getId());
            List<Ca> caList = caAdminService.findAllListCa();

            for (Ca ca : caList) {
                Time thoiGianBatDauHT = ca.getThoiGianBatDau();
                Time thoiGianKetThucHT = ca.getThoiGianKetThuc();

                if ((caAdminRequest.getThoiGianBatDau().equals(caCu.getThoiGianBatDau()) || caAdminRequest.getThoiGianBatDau().after(caCu.getThoiGianBatDau()))
                        && (caAdminRequest.getThoiGianKetThuc().equals(caCu.getThoiGianKetThuc()) || caAdminRequest.getThoiGianKetThuc().before(caCu.getThoiGianKetThuc()))) {
                    caAdminService.saveOrUpdate(caAdminRequest);

                    try {
                        logger.info("********** bắt đầu xóa job hủy lịch **********");
                        runJobHuyLichSanCa.xoaJobHuyLich();
                        logger.info("********** kết thúc xóa job hủy lịch **********");

                        runJobHuyLichSanCa.khaiBaoInfoJob();

                    }catch (Exception e){

                    }
                    return new BaseResponse<>(HttpStatus.OK, "Thành công");
                }

                if (caAdminRequest.getThoiGianBatDau().equals(caCu.getThoiGianBatDau()) && caAdminRequest.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())
                        && !caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)
                        && (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || (caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) && caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT)) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT))) {
                    return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                }

                if (caAdminRequest.getThoiGianKetThuc().equals(caCu.getThoiGianKetThuc()) && caAdminRequest.getThoiGianBatDau().before(caCu.getThoiGianBatDau())
                        && !caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)
                        && (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || (caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) && caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT)) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT))) {
                    return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                }

                if (caAdminRequest.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && caAdminRequest.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())
                        && !caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)
                        && (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || (caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) && caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT)) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT))) {
                    return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                }
            }

            caAdminService.saveOrUpdate(caAdminRequest);

            return new BaseResponse<>(HttpStatus.OK, "Thành công");

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Lỗi");
        }
    }


    @DeleteMapping("delete/{id}")
    public BaseResponse<?> deleteById(@PathVariable("id") String id) {
        try {
            caAdminService.deleteById(id);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @GetMapping("/export")
    public BaseResponse<?> export(HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Ca" + "." + "xlsx";
            response.setHeader(headerKey, headerValue);
            CaExportExcel.exportData(response, caAdminService.findAllListCa());
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PostMapping("/import")
    public BaseResponse<?> importEcel(@RequestBody MultipartFile file) throws IOException {
        try {
            Set<CheckTimeListReQuest> listCheck = new HashSet<>();
            List<Ca> caList = CaImportExcel.readExcel(file);
            List<Ca> findAllCaList = caAdminService.findAllListCa();

            for (Ca caCheck : caList) {
                for (Ca caCu : findAllCaList) {
                    if (!caCheck.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && !caCheck.getThoiGianBatDau().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                    if (!caCheck.getThoiGianKetThuc().before(caCu.getThoiGianBatDau()) && !caCheck.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                    if (caCheck.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && caCheck.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                }
            }

            if (listCheck.size() > 0) {
                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, listCheck);
            }

            caAdminService.saveAll(caList);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }

    }
}
