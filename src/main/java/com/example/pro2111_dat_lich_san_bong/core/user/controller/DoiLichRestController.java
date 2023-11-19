package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichOneRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.HoaDonUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichDatResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichOneUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.LoaiSanAndCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.DoiLichUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/doi-lich")
@CrossOrigin("*")
public class DoiLichRestController {

    @Autowired
    private CaUserService caUserService;

    @Autowired
    private LoaiSanUserService loaiSanUserService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private ThoiGianDLUserServiver thoiGianDLUserServiver;

    @Autowired
    private DoiLichUserService doiLichUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;


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

    //    @PostMapping("/lich-chua-xn")
//    public ResponseEntity<?> doiLichChuaXn(@RequestBody List<HoaDonUserRequest> list) throws ParseException {
//        SimpleDateFormat dateOriginal = new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        for (HoaDonUserRequest request : list) {
//            LoaiSan loaiSan = loaiSanUserService.finLoaiSanByTeLoaiSan(request.getLoaiSanCu());
//            Ca ca = caUserService.findFirstByTenCa(request.getCaCu());
//            System.out.println(request.getCaNew());
//            System.out.println(request.getLoaiSanNew());
//            System.out.println(request.getGiaNew());
//            System.out.println(request.getNgayNew());
//            ThoiGianDatLich thoiGianDatLich = thoiGianDLUserServiver.findTGDLByIdCaAndIdLsAndNgayDa(request.getId(),
//                    format.parse(format.format(dateOriginal.parse(request.getNgayDaCu()))),
//                    ca.getId(), loaiSan.getId());
//
//        }
//        return ResponseEntity.ok("OKE");
//    }
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
    public ResponseEntity<?> updateLichDat(@RequestBody DoiLichOneRequest doiLichOneRequest) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayDenSan = LocalDate.parse(doiLichOneRequest.getNgayDoi(), timeFormatter);

            Date date = simpleDateFormat.parse(doiLichOneRequest.getNgayDoi());

            int a = sanCaUserService.checkSanCa(doiLichOneRequest.getIdLoaiSan(), doiLichOneRequest.getIdCa(), doiLichOneRequest.getNgayDoi());
            if (a == 1) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Đã có lịch đặt!"));
            }
            String idCaAndLoaiSan = doiLichOneRequest.getIdCa() + "+" + doiLichOneRequest.getIdLoaiSan() + "+" + dateFormat.format(date);
            String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, doiLichOneRequest.getIdLoaiSan());


            HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(doiLichOneRequest.getIdHDSC());
            SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());

            HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
            hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
            hoaDonSanCaUpdate.setTienSan(Double.valueOf(doiLichOneRequest.getTienSan()));
            hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
            hoaDonSanCaUpdate.setTrangThai(hoaDonSanCa.getTrangThai());
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

            //cap nhat
            hoaDonSanCaUserService.saveHoaDonSanCa(hoaDonSanCaUpdate);
            sanCaUserService.saveSanCa(sanCaUpdate);

            //delete
            hoaDonSanCaUserService.deleteByIdHoaDonSanCa(doiLichOneRequest.getIdHDSC());
            sanCaUserService.deleteSanCaById(sanCa.getId());


            System.out.println(idSanCa);
            System.out.println(doiLichOneRequest.getIdSanBong());
            System.out.println(doiLichOneRequest.getIdCa());
            System.out.println(doiLichOneRequest.getIdLoaiSan());


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

}
