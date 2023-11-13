package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.HoaDonUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichDatResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LoaiSanUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ThoiGianDLUserServiver;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
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

    @PostMapping("/lich-chua-xn")
    public ResponseEntity<?> doiLichChuaXn(@RequestBody List<HoaDonUserRequest> list) throws ParseException {
        SimpleDateFormat dateOriginal = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (HoaDonUserRequest request : list) {
            LoaiSan loaiSan = loaiSanUserService.finLoaiSanByTeLoaiSan(request.getLoaiSanCu());
            Ca ca = caUserService.findFirstByTenCa(request.getCaCu());
            System.out.println(request.getCaNew());
            System.out.println(request.getLoaiSanNew());
            System.out.println(request.getGiaNew());
            System.out.println(request.getNgayNew());
            ThoiGianDatLich thoiGianDatLich = thoiGianDLUserServiver.findTGDLByIdCaAndIdLsAndNgayDa(request.getId(),
                    format.parse(format.format(dateOriginal.parse(request.getNgayDaCu()))),
                    ca.getId(), loaiSan.getId());

        }
        return ResponseEntity.ok("OKE");
    }
}
