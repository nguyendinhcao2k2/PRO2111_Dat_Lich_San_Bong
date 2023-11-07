package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeCaNamCaTrongNgayResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoCaAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongKeTheoNamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.ThongKeAdminService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/thong-ke")
public class ThongKeRestController {
    @Autowired
    private ThongKeAdminService thongKeAdminService;

    private static LocalDate date = LocalDate.now();


    @GetMapping("")
    public ResponseEntity<?> thongKeCaNamAndCaTrongNgay(@RequestParam(value = "year") Optional<Integer> year,
                                                        @RequestParam(value = "date") Optional<LocalDate> valueDate) {

        List<ThongKeTheoNamAdminResponse> sum = thongKeAdminService.sumTongTienByMonthAndYear(year.orElse(date.getYear()));
        ThongKeCaNamCaTrongNgayResponse thongKeAll = new ThongKeCaNamCaTrongNgayResponse(sum, caAdminResponses(valueDate), caAdminResponses(Optional.of(date)));
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, thongKeAll));
    }

    // thong ke theo ngay
    private List<ThongKeTheoCaAdminResponse> caAdminResponses(Optional<LocalDate> valueDate) {
        Double ca1 = sumPriceInCa(valueDate, "00:00:01", "06:00:00");
        Double ca2 = sumPriceInCa(valueDate, "06:01:00", "12:00:00");
        Double ca3 = sumPriceInCa(valueDate, "12:01:00", "18:00:00");
        Double ca4 = sumPriceInCa(valueDate, "18:01:00", "23:59:59");
        List<ThongKeTheoCaAdminResponse> caAdminResponses = new ArrayList<ThongKeTheoCaAdminResponse>();
        caAdminResponses.add(baseEntityTK("ca1", ca1));
        caAdminResponses.add(baseEntityTK("ca2", ca2));
        caAdminResponses.add(baseEntityTK("ca3", ca3));
        caAdminResponses.add(baseEntityTK("ca4", ca4));
        return caAdminResponses;
    }

    private Double sumPriceInCa(Optional<LocalDate> dateOptional, String timeStart, String timeEnd) {
        return thongKeAdminService.thongKeTheoCaTrongNgay(dateOptional.orElse(date).getDayOfMonth(), dateOptional.orElse(date).getMonthValue(), dateOptional.orElse(date).getYear(), timeStart, timeEnd);
    }

    private ThongKeTheoCaAdminResponse baseEntityTK(String ca, Double value) {
        return new ThongKeTheoCaAdminResponse(ca, value);
    }

    //    @GetMapping("/ca-trong-ngay")
//    public ResponseEntity<?> thongKeCaTrongNgay(Optional<LocalDate> valueDate) {
//        try {
//            Double ca1 = sumPriceInCa(valueDate, "00:01:00", "06:00:00");
//            Double ca2 = sumPriceInCa(valueDate, "06:01:00", "12:00:00");
//            Double ca3 = sumPriceInCa(valueDate, "12:01:00", "18:00:00");
//            Double ca4 = sumPriceInCa(valueDate, "18:01:00", "00:00:00");
//            List<ThongKeTheoCaAdminResponse> caAdminResponses = new ArrayList<ThongKeTheoCaAdminResponse>();
//            caAdminResponses.add(baseEntityTK("ca1", ca1));
//            caAdminResponses.add(baseEntityTK("ca2", ca2));
//            caAdminResponses.add(baseEntityTK("ca3", ca3));
//            caAdminResponses.add(baseEntityTK("ca4", ca4));
//            List<ThongKeTheoNamAdminResponse> sum = thongKeAdminService.sumTongTienByMonthAndYear(date.getYear());
//            List<ThongKeLoaiDichVuAdminResponse> thongKeLoaiDichVuAdminResponses = thongKeAdminService.findThongKeDoThueTheoNam(date.getYear());
//            ThongKeCaNamCaTrongNgayResponse thongKeAll = new ThongKeCaNamCaTrongNgayResponse(sum, caAdminResponses,thongKeLoaiDichVuAdminResponses);
//            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, thongKeAll));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.ofNullable(new BaseResponse<Object>(HttpStatus.SERVICE_UNAVAILABLE, "Error"));
//        }
//    }
    @GetMapping("/do-thue")
    public ResponseEntity<?> thongKeDoThue(@RequestParam(value = "year") Optional<Integer> year) {
        List doThueTheoNam = thongKeAdminService.findThongKeDoThueTheoNam(year.orElse(date.getYear()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }

    @GetMapping("/do-thue/thang")
    public ResponseEntity<?> thongKeDoThueTheoThang(@RequestParam(value = "year") Optional<Integer> year,
                                                    @RequestParam(value = "month") Optional<Integer> month) {
        List doThueTheoNam = thongKeAdminService.findThongKeDoThueTheoThangTrongNam(year.orElse(date.getYear()), month.orElse(date.getMonthValue()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }

    @GetMapping("/do-thue/ngay")
    public ResponseEntity<?> thongKeDoThueTheoNgay(@RequestParam(value = "year") Optional<Integer> year,
                                                   @RequestParam(value = "month") Optional<Integer> month,
                                                   @RequestParam(value = "day") Optional<Integer> day
    ) {
        List doThueTheoNam = thongKeAdminService.findThongKeDoThueTheoNgay(year.orElse(date.getYear()), month.orElse(date.getMonthValue()), day.orElse(date.getDayOfMonth()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }

    // nuoc uong
    @GetMapping("/nuoc-uong")
    public ResponseEntity<?> thongKeNuocUong(@RequestParam(value = "year") Optional<Integer> year) {
        List doThueTheoNam = thongKeAdminService.findThongKeNuocUongTheoNam(year.orElse(date.getYear()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }

    @GetMapping("/nuoc-uong/thang")
    public ResponseEntity<?> thongKeNuocUongTheoThang(@RequestParam(value = "year") Optional<Integer> year,
                                                      @RequestParam(value = "month") Optional<Integer> month) {
        List doThueTheoNam = thongKeAdminService.findThongKeNuocUongTheoThangTrongNam(year.orElse(date.getYear()), month.orElse(date.getMonthValue()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }

    @GetMapping("/nuoc-uong/ngay")
    public ResponseEntity<?> thongKeNuocUongTheoNgay(@RequestParam(value = "year") Optional<Integer> year,
                                                     @RequestParam(value = "month") Optional<Integer> month,
                                                     @RequestParam(value = "day") Optional<Integer> day
    ) {
        List doThueTheoNam = thongKeAdminService.findThongKeNuocUongTheoNgay(year.orElse(date.getYear()), month.orElse(date.getMonthValue()), day.orElse(date.getDayOfMonth()));
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, doThueTheoNam));
    }
}
