package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichOneRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.HoaDonUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichDatResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.LoaiSanAndCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.DoiLichUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/doi-lich")
@CrossOrigin("*")
public class DoiLichOneRestController {

    @Autowired
    private CaUserService caUserService;

    @Autowired
    private LoaiSanUserService loaiSanUserService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private DoiLichUserService doiLichUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private HoaDonDoiLichUserService hoaDonDoiLichUserService;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;


    @GetMapping()
    public ResponseEntity<?> doiLich() {
        try {
            DoiLichDatResponse response = new DoiLichDatResponse();
            response.setCa(caUserService.findAll());
            response.setLoaiSan(loaiSanUserService.findAll());
            response.setHoaDon(hoaDonUserService.findHoaDonByChoXacNhanAndAccHT(commonSession.getUserId()));
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, response));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<?> doiLich1(@PathVariable("id") String idSanCa) {
        try {
            DoiLichOneUserResponse response = doiLichUserService.findFirstByIdSanCaAndTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal(), idSanCa);
            boolean chekNgayDenSan = doiLichUserService.checkNgayDenSan(response.getNgayDenSan());
            if (chekNgayDenSan) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, response));
            }
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ALREADY_REPORTED, response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/list-san-list-ca")
    public ResponseEntity<?> getAllLoaiSanAndCa() {
        try {
            LoaiSanAndCaResponse response = new LoaiSanAndCaResponse();
            response.setCa(caUserService.findAll());
            response.setLoaiSan(loaiSanUserService.findAll());
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Ko có value"));
        }
    }

    @PostMapping("update-lich")
    public ResponseEntity<?> updateLichDat(@RequestBody DoiLichOneRequest doiLichOneRequest, HttpServletRequest request) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayDenSan = LocalDate.parse(doiLichOneRequest.getNgayDoi(), timeFormatter);

            Date date = simpleDateFormat.parse(doiLichOneRequest.getNgayDoi());

            String idCaAndLoaiSan = doiLichOneRequest.getIdCa() + "+" + doiLichOneRequest.getIdLoaiSan() + "+" + dateFormat.format(date);
            String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, doiLichOneRequest.getIdLoaiSan());


            HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(doiLichOneRequest.getIdHDSC());
            SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());
            HoaDon hoaDon = hoaDonDoiLichUserService.findById(hoaDonSanCa.getIdHoaDon());

            HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
            hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
            hoaDonSanCaUpdate.setTienSan(Double.valueOf(doiLichOneRequest.getTienSan()));
            hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
            hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
            hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
            hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
            hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
            hoaDonSanCaUpdate.setCountLich(1);
            hoaDonSanCaUpdate.setTienCocThua(Double.valueOf(doiLichOneRequest.getTienCocThua()));
            //id san ca
            String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;
            hoaDonSanCaUpdate.setIdSanCa(idSanCa);


            //san ca
            SanCa sanCaUpdate = new SanCa();
            sanCaUpdate.setNgayDenSan(ngayDenSan);
            sanCaUpdate.setGia(Double.valueOf(doiLichOneRequest.getTienSan()));
            sanCaUpdate.setIdCa(doiLichOneRequest.getIdCa());
            sanCaUpdate.setId(idSanCa);
            sanCaUpdate.setTrangThai(sanCa.getTrangThai());
            sanCaUpdate.setIdSanBong(idSanBongCheck);
            sanCaUpdate.setUserId(sanCa.getUserId());
            sanCaUpdate.setThoiGianDat(sanCa.getThoiGianDat());


            //cap nhat hoa don
            hoaDon.setTongTien((hoaDon.getTongTien() - hoaDonSanCa.getTienSan()) + Double.valueOf(doiLichOneRequest.getTienSan()));
//            hoaDon.setTienCoc(hoaDon.getTienCoc() + doiLichOneRequest.getTienCocThieu());
            hoaDonDoiLichUserService.update(hoaDon);
            //create hoa don san ca,san ca
            hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaUpdate);
            sanCaUserService.saveSanCa(sanCaUpdate);

            //delete
            hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCa.getId());
            sanCaUserService.deleteSanCaById(sanCa.getId());

            //create lich su san bong
            LichSuSanBong lichSuSanBong = new LichSuSanBong();
            lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DOI_LICH.ordinal());
            lichSuSanBong.setNgayThucHien(LocalDateTime.now());
            lichSuSanBongAdminService.createOrUpdate(lichSuSanBong);


//            gửi mail
            List<HoaDonSendMailResponse> list = hoaDonSanCaUserService.getLisTHDSC(hoaDon.getId());
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (list.size() == 1) {
                HoaDonSendMailResponse response = list.get(0);

                SendMailRequest sendMailRequest = new SendMailRequest();
                sendMailRequest.setTitle("Phiếu Đặt Lịch");
                sendMailRequest.setNguoiDat(response.getDisplayName());
                sendMailRequest.setNguoiNhanMail(response.getEmail());
                sendMailRequest.setQrCodeData(response.getMaQR());
                sendMailRequest.setNgayDat(formatter.format(response.getNgayTao()));
                sendMailRequest.setTimeStart(response.getThoiGianBatDau());
                sendMailRequest.setTimeEnd(response.getThoiGianKetThuc());
                sendMailRequest.setGiaTien(response.getTienSan());
                sendMailRequest.setNgayCheckIn(formatterNgayDa.format(response.getNgayDenSan()));

                sendMailUtils.sendEmail(sendMailRequest);
            } else if (list.size() > 1) {

                Context context = new Context();
                context.setVariable("nguoiDat", hoaDon.getTenNguoiDat());
                context.setVariable("sdt", hoaDon.getSoDienThoaiNguoiDat());

                //thời gian đặt sân
                context.setVariable("timeDat", hoaDon.getNgayTao());
                context.setVariable("tongTien", decimalFormat.format(hoaDon.getTongTien()));

                List<MaillListResponse> listResponses = new ArrayList<>();

                for (HoaDonSendMailResponse response : list) {
                    MaillListResponse maillListResponse = new MaillListResponse();
                    maillListResponse.setIdHoaDonSanCa(response.getId());
                    maillListResponse.setGiaSan(decimalFormat.format(response.getTienSan()));
                    maillListResponse.setNgayDa(formatterNgayDa.format(response.getNgayDenSan()));
                    maillListResponse.setCa(response.getTenCa() + ": (" + response.getThoiGianBatDau() + "-" + response.getThoiGianKetThuc() + ")");
                    listResponses.add(maillListResponse);
                }

                context.setVariable("thoiGianList", listResponses);
                sendMailWithBookings.sendEmailBookings(hoaDon.getEmail(), context, request);
            }
//            gửi mail

            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Oke"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("find-by-tien-coc")
    public ResponseEntity<?> findPhanTramTienCoc() {
        try {
            SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC);
            if (param == null) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, 0));
            }
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, param.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, 0));
        }
    }

    @GetMapping("check-lick-dat")
    public ResponseEntity<?> checkLichDat(
            @RequestParam("idLS") String idLS,
            @RequestParam("idCa") String idCa,
            @RequestParam("ngayDoi") String ngayDoi
    ) {
        try {
            int a = sanCaUserService.checkSanCa(idLS, idCa, ngayDoi);
            System.out.println("hello:" + a);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, a));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, 2));
        }
    }

}
