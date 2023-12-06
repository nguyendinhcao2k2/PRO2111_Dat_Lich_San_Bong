package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.*;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/thong-ke")
public class ThongKeRestController {
    @Autowired
    private ThongKeAdminService thongKeAdminService;

    private static LocalDate date = LocalDate.now();

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;
    @Autowired
    private HoaDonThongKeAdminService hoaDonThongKeAdminService;
    @Autowired
    private HoaDonSanCaThongKeAdminService hoaDonSanCaThongKeAdminService;
    @Autowired
    private GiaoCaThongKeAdminService giaoCaThongKeAdminService;

    @Autowired
    private ViTienCocAdminService viTienCocAdminService;

    //định dạng chuỗi ngày
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/ngay")
    public ResponseEntity<?> getListThongke(@RequestParam("paramNgay") Optional<LocalDate> paramNgay) {
        // theo ngay
        Double tongDoanhThuTheoNgay = hoaDonSanCaThongKeAdminService.tongTienTheoNgay(paramNgay.orElse(date));
        Double tongTienMatTheoNgay = hoaDonSanCaThongKeAdminService.tongTienMatTheoNgayVaHinhThucThanhToan(paramNgay.orElse(date), LoaiHinhThanhToan.TIEN_MAT.ordinal());
        Double tongTienMatTienCocTheoNgay = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNgay(LoaiHinhThanhToan.TIEN_MAT.ordinal(),paramNgay.orElse(date));
        Double tongTienChuyenKhoanTheoNgay = hoaDonSanCaThongKeAdminService.tongTienMatTheoNgayVaHinhThucThanhToan(paramNgay.orElse(date), LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());
        Double tongTienChuyenKhoanCocTheoNgay = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNgay(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal(),paramNgay.orElse(date));
        Double tongTienPhatSinhTrongCaTheoNgay = giaoCaThongKeAdminService.tongTienPhatSinhCuaCacCaTheoNgay(paramNgay.orElse(date));
        Integer luotSanDatOnlineTheoNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal(), paramNgay.orElse(date));
        Integer luotSanDatNhoTheoNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal(), paramNgay.orElse(date));
        Integer tongSoLuotDaTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal(), paramNgay.orElse(date));
        Integer tongSoLuotChuyenLichTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.DOI_LICH.ordinal(), paramNgay.orElse(date));
        Integer tongSoLuotHuyTrongNgay = lichSuSanBongAdminService.thongKeSoLuot(TrangThaiLichSuSanBong.HUY_LICH.ordinal(), paramNgay.orElse(date));
        ThongKeAdminReponse thongKeAdminTheoNgay = new ThongKeAdminReponse();
        thongKeAdminTheoNgay.setTongDoanhThu(tongDoanhThuTheoNgay + tongTienMatTienCocTheoNgay + tongTienChuyenKhoanCocTheoNgay);
        thongKeAdminTheoNgay.setTongSoTienMat(tongTienMatTheoNgay + tongTienMatTienCocTheoNgay);
        thongKeAdminTheoNgay.setTongSoTienChuyenKhoan(tongTienChuyenKhoanTheoNgay + tongTienChuyenKhoanCocTheoNgay);
        thongKeAdminTheoNgay.setTongTienPhatSinhKhiGiaoCa(tongTienPhatSinhTrongCaTheoNgay);
        thongKeAdminTheoNgay.setTongSanDatOnline(luotSanDatOnlineTheoNgay);
        thongKeAdminTheoNgay.setTongSoLuotSanDatNho(luotSanDatNhoTheoNgay);
        thongKeAdminTheoNgay.setTongSoLuotDa(tongSoLuotDaTrongNgay);
        thongKeAdminTheoNgay.setTongSoLuotChuyenLich(tongSoLuotChuyenLichTrongNgay);
        thongKeAdminTheoNgay.setTongSoHuyLich(tongSoLuotHuyTrongNgay);
        // theo ngay


        ThongKeAdminAllResponse thongKeAdminAllResponse = new ThongKeAdminAllResponse();
        thongKeAdminAllResponse.setThongKeNgay(thongKeAdminTheoNgay);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, thongKeAdminAllResponse));
    }

    @GetMapping("/tuan")
    public ResponseEntity<?> getListThongkeTuan(@RequestParam("paramTuan") Optional<Integer> paramTuan) {

        //theo tuan
        DateTimeFormatter formatterTuan = DateTimeFormatter.ofPattern("YYYYww");
        Integer tuanHT = Integer.valueOf(date.format(formatterTuan));
        Double tongDoanhThuTheoTuan = hoaDonSanCaThongKeAdminService.tongTienTheoTuan(paramTuan.orElse(tuanHT));
        Double tongTienMatTheoTuan = hoaDonSanCaThongKeAdminService.tongTienMatTheoTuanVaHinhThucThanhToan(paramTuan.orElse(tuanHT), LoaiHinhThanhToan.TIEN_MAT.ordinal());
        Double tongTienMatTienCocTheoTuan = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanTuan(LoaiHinhThanhToan.TIEN_MAT.ordinal(),paramTuan.orElse(tuanHT));
        Double tongTienChuyenKhoanTienCocTheoTuan = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanTuan(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal(),paramTuan.orElse(tuanHT));
        Integer luotSanDatOnlineTheoTuan = lichSuSanBongAdminService.thongKeSoLuotTheoTuan(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal(), paramTuan.orElse(tuanHT));
        Integer luotSanDatNhoTheoTuan = lichSuSanBongAdminService.thongKeSoLuotTheoTuan(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal(), paramTuan.orElse(tuanHT));
        Integer tongSoLuotDaTrongTuan = lichSuSanBongAdminService.thongKeSoLuotTheoTuan(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal(), paramTuan.orElse(tuanHT));
        Integer tongSoLuotChuyenLichTrongTuan = lichSuSanBongAdminService.thongKeSoLuotTheoTuan(TrangThaiLichSuSanBong.DOI_LICH.ordinal(), paramTuan.orElse(tuanHT));
        Integer tongSoLuotHuyTrongTuan = lichSuSanBongAdminService.thongKeSoLuotTheoTuan(TrangThaiLichSuSanBong.HUY_LICH.ordinal(), paramTuan.orElse(tuanHT));
        ThongKeAdminReponse thongKeAdminTheoTuan = new ThongKeAdminReponse();
        thongKeAdminTheoTuan.setTongDoanhThu(tongDoanhThuTheoTuan + tongTienMatTienCocTheoTuan + tongTienChuyenKhoanTienCocTheoTuan);
        thongKeAdminTheoTuan.setTongSoTienMat(tongTienMatTheoTuan + tongTienMatTienCocTheoTuan);
        thongKeAdminTheoTuan.setTongSanDatOnline(luotSanDatOnlineTheoTuan);
        thongKeAdminTheoTuan.setTongSoLuotSanDatNho(luotSanDatNhoTheoTuan);
        thongKeAdminTheoTuan.setTongSoLuotDa(tongSoLuotDaTrongTuan);
        thongKeAdminTheoTuan.setTongSoLuotChuyenLich(tongSoLuotChuyenLichTrongTuan);
        thongKeAdminTheoTuan.setTongSoHuyLich(tongSoLuotHuyTrongTuan);
        //theo tuan

        ThongKeAdminAllResponse thongKeAdminAllResponse = new ThongKeAdminAllResponse();
        thongKeAdminAllResponse.setThongKeTuan(thongKeAdminTheoTuan);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, thongKeAdminAllResponse));
    }

    @GetMapping("/thang")
    public ResponseEntity<?> getListThongkeThang(@RequestParam("paramMonth") Optional<LocalDate> paramMonth,
                                                 @RequestParam("thangThanhToan") Optional<Integer> thangThanhToan,
                                                 @RequestParam("namThanhToan") Optional<Integer> namThanhToan) {


        //theo thang
        Double tongDoanhThuTheoThang = hoaDonSanCaThongKeAdminService.tongTienTheoThang(paramMonth.orElse(date));
        Double tongTienMatTheoThang = hoaDonSanCaThongKeAdminService.tongTienMatTheoThangVaHinhThucThanhToan(thangThanhToan.orElse(date.getMonthValue()), namThanhToan.orElse(date.getYear()), LoaiHinhThanhToan.TIEN_MAT.ordinal());
        Double tongTienMatTienCocTheoThang = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanThang(LoaiHinhThanhToan.TIEN_MAT.ordinal(),thangThanhToan.orElse(date.getMonthValue()), namThanhToan.orElse(date.getYear()));
        Double tongTienChuyenKhoanTienCocTheoThang = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanThang(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal(),thangThanhToan.orElse(date.getMonthValue()), namThanhToan.orElse(date.getYear()));
        Integer luotSanDatOnlineTheoThang = lichSuSanBongAdminService.thongKeSoLuotTheoThang(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal(), paramMonth.orElse(date));
        Integer luotSanDatNhoTheoThang = lichSuSanBongAdminService.thongKeSoLuotTheoThang(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal(), paramMonth.orElse(date));
        Integer tongSoLuotDaTrongThang = lichSuSanBongAdminService.thongKeSoLuotTheoThang(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal(), paramMonth.orElse(date));
        Integer tongSoLuotChuyenLichTrongThang = lichSuSanBongAdminService.thongKeSoLuotTheoThang(TrangThaiLichSuSanBong.DOI_LICH.ordinal(), paramMonth.orElse(date));
        Integer tongSoLuotHuyTrongThang = lichSuSanBongAdminService.thongKeSoLuotTheoThang(TrangThaiLichSuSanBong.HUY_LICH.ordinal(), paramMonth.orElse(date));
        ThongKeAdminReponse thongKeAdminTheoThang = new ThongKeAdminReponse();
        thongKeAdminTheoThang.setTongDoanhThu(tongDoanhThuTheoThang + tongTienMatTienCocTheoThang + tongTienChuyenKhoanTienCocTheoThang);
        thongKeAdminTheoThang.setTongSoTienMat(tongTienMatTheoThang + tongTienMatTienCocTheoThang);
        thongKeAdminTheoThang.setTongSanDatOnline(luotSanDatOnlineTheoThang);
        thongKeAdminTheoThang.setTongSoLuotSanDatNho(luotSanDatNhoTheoThang);
        thongKeAdminTheoThang.setTongSoLuotDa(tongSoLuotDaTrongThang);
        thongKeAdminTheoThang.setTongSoLuotChuyenLich(tongSoLuotChuyenLichTrongThang);
        thongKeAdminTheoThang.setTongSoHuyLich(tongSoLuotHuyTrongThang);
        //theo thang

        ThongKeAdminAllResponse thongKeAdminAllResponse = new ThongKeAdminAllResponse();
        thongKeAdminAllResponse.setThongKeThang(thongKeAdminTheoThang);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, thongKeAdminAllResponse));
    }

    @GetMapping("/nam")
    public ResponseEntity<?> getListThongkeNam(@RequestParam("paramYear") Optional<Integer> paramYear) {


        //theo nam
        Double tongDoanhThuTheoNam = hoaDonSanCaThongKeAdminService.tongTienTheoNam(paramYear.orElse(date.getYear()));
        Double tongTienMatTheoNam = hoaDonSanCaThongKeAdminService.tongTienMatTheoNamVaHinhThucThanhToan(paramYear.orElse(date.getYear()), LoaiHinhThanhToan.TIEN_MAT.ordinal());
        Double tongTienMatTienCocTheoNam = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNam(LoaiHinhThanhToan.TIEN_MAT.ordinal(),paramYear.orElse(date.getYear()));
        Double tongTienChuyenKhoanTienCocTheoNam = viTienCocAdminService.tongTienThanhToanCocTheoHinhThucThanhToanNam(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal(),paramYear.orElse(date.getYear()));
        Integer luotSanDatOnlineTheoNam = lichSuSanBongAdminService.thongKeSoLuotTheoNam(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal(), paramYear.orElse(date.getYear()));
        Integer luotSanDatNhoTheoNam = lichSuSanBongAdminService.thongKeSoLuotTheoNam(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal(), paramYear.orElse(date.getYear()));
        Integer tongSoLuotDaTrongNam = lichSuSanBongAdminService.thongKeSoLuotTheoNam(TrangThaiLichSuSanBong.DA_CHECK_IN_DA_BONG.ordinal(), paramYear.orElse(date.getYear()));
        Integer tongSoLuotChuyenLichTrongNam = lichSuSanBongAdminService.thongKeSoLuotTheoNam(TrangThaiLichSuSanBong.DOI_LICH.ordinal(), paramYear.orElse(date.getYear()));
        Integer tongSoLuotHuyTrongNam = lichSuSanBongAdminService.thongKeSoLuotTheoNam(TrangThaiLichSuSanBong.HUY_LICH.ordinal(), paramYear.orElse(date.getYear()));
        ThongKeAdminReponse thongKeAdminTheoNam = new ThongKeAdminReponse();
        thongKeAdminTheoNam.setTongDoanhThu(tongDoanhThuTheoNam + tongTienMatTienCocTheoNam + tongTienChuyenKhoanTienCocTheoNam);
        thongKeAdminTheoNam.setTongSoTienMat(tongTienMatTheoNam + tongTienMatTienCocTheoNam);
        thongKeAdminTheoNam.setTongSanDatOnline(luotSanDatOnlineTheoNam);
        thongKeAdminTheoNam.setTongSoLuotSanDatNho(luotSanDatNhoTheoNam);
        thongKeAdminTheoNam.setTongSoLuotDa(tongSoLuotDaTrongNam);
        thongKeAdminTheoNam.setTongSoLuotChuyenLich(tongSoLuotChuyenLichTrongNam);
        thongKeAdminTheoNam.setTongSoHuyLich(tongSoLuotHuyTrongNam);
        //theo nam

        ThongKeAdminAllResponse thongKeAdminAllResponse = new ThongKeAdminAllResponse();
        thongKeAdminAllResponse.setThongKeNam(thongKeAdminTheoNam);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, thongKeAdminAllResponse));
    }

    @GetMapping("")
    public ResponseEntity<?> thongKeCaNamAndCaTrongNgay(@RequestParam(value = "year") Optional<Integer> year,
                                                        @RequestParam(value = "date") Optional<LocalDate> valueDate) {
        List<ViTienCocNamReponse> viTienCocNamReponses = viTienCocAdminService.findListTienCocNam(year.orElse(date.getYear()));
        List<ThongKeTheoNamAdminResponse> sum = thongKeAdminService.sumTongTienByMonthAndYear(year.orElse(date.getYear()));
        if(viTienCocNamReponses != null){
            for (ThongKeTheoNamAdminResponse response:sum) {
                for(ViTienCocNamReponse viTienCoc : viTienCocNamReponses){
                    if(response.getMonthName() == viTienCoc.getMonthName()){
                        response.setTotalPrice(response.getTotalPrice() + viTienCoc.getTotalPrice());
                    }
                }
            }
        }

        ThongKeCaNamCaTrongNgayResponse thongKeAll = new ThongKeCaNamCaTrongNgayResponse(sum, caAdminResponses(valueDate), caAdminResponses(Optional.of(date)));
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, thongKeAll));
    }

    // thong ke theo ngay
    private List<ThongKeTheoCaAdminResponse> caAdminResponses(Optional<LocalDate> valueDate) {
        Double ca1 = sumPriceInCa(valueDate, "00:00:01", "06:00:00");
        Double ca2 = sumPriceInCa(valueDate, "06:01:00", "12:00:00");
        Double ca3 = sumPriceInCa(valueDate, "12:01:00", "18:00:00");
        Double ca4 = sumPriceInCa(valueDate, "18:01:00", "23:59:59");

        Double tienCocCa1 = sumTienCocTheoCa(valueDate, "00:00:01", "06:00:00");
        Double tienCocCa2 = sumTienCocTheoCa(valueDate, "06:01:00", "12:00:00");
        Double tienCocCa3 = sumTienCocTheoCa(valueDate, "12:01:00", "18:00:00");
        Double tienCocCa4 = sumTienCocTheoCa(valueDate, "18:01:00", "23:59:59");
        List<ThongKeTheoCaAdminResponse> caAdminResponses = new ArrayList<ThongKeTheoCaAdminResponse>();
        caAdminResponses.add(baseEntityTK("ca1", ca1 + tienCocCa1));
        caAdminResponses.add(baseEntityTK("ca2", ca2 + tienCocCa2));
        caAdminResponses.add(baseEntityTK("ca3", ca3 + tienCocCa3));
        caAdminResponses.add(baseEntityTK("ca4", ca4 + tienCocCa4));
        caAdminResponses.add(baseEntityTK("ca4", ca4));
        return caAdminResponses;
    }
    private Double sumTienCocTheoCa(Optional<LocalDate> dateOptional, String timeStart, String timeEnd){
        Double tien = viTienCocAdminService.tongTienThanhToanCocTrongNgayTheoCa(dateOptional.orElse(date).getDayOfMonth(), dateOptional.orElse(date).getMonthValue(), dateOptional.orElse(date).getYear(), timeStart, timeEnd);
        if(tien == null){
            return 0.0;
        }
        return tien;
    }
    private Double sumPriceInCa(Optional<LocalDate> dateOptional, String timeStart, String timeEnd) {
        Double tien = thongKeAdminService.thongKeTheoCaTrongNgay(dateOptional.orElse(date).getDayOfMonth(), dateOptional.orElse(date).getMonthValue(), dateOptional.orElse(date).getYear(), timeStart, timeEnd);
        if(tien == null){
            return 0.0;
        }
        return tien;
    }

    private ThongKeTheoCaAdminResponse baseEntityTK(String ca, Double value) {
        return new ThongKeTheoCaAdminResponse(ca, value);
    }

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
