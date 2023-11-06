package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.GiaoCaAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.utils.GiaoCaExportExcel;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/giao-ca")
public class GiaoCaAdminRestController {

    @Autowired
    private GiaoCaAdminService giaoCaAdminService;


    private Pageable basePageable(Optional<Integer> page, Optional<Integer> pageSize) {
        return PageRequest.of(page.orElse(0), pageSize.orElse(10));
    }

    private ResponseEntity<?> baseRepon(Page<QuanLyGiaoCaResponse> giaoCaResponsePage) {
        PageableObject<QuanLyGiaoCaResponse> giaoCaResponsePageableObject = new PageableObject<QuanLyGiaoCaResponse>(giaoCaResponsePage);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponsePageableObject));
    }

    @GetMapping("/owners")
    public ResponseEntity<?> ownersGiaoCa(Optional<Integer> page, Optional<Integer> pageSize) {
        return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(basePageable(page, pageSize), TrangThaiGiaoCa.KET_THUC_CA));
    }

    @GetMapping("search")
    public ResponseEntity<?> searchGiaoCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("name") String name) {
        Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.searchByName(basePageable(page, pageSize), name);
        return baseRepon(giaoCaResponsePage);
    }

    @GetMapping("rut-tien")
    public ResponseEntity<?> giaoCaCoTienRut(Optional<Integer> page, Optional<Integer> pageSize) {
        return baseRepon(giaoCaAdminService.giaoCaCoTienRut(basePageable(page, pageSize)));
    }

    @GetMapping("by-time")
    public ResponseEntity<?> giaoCaByThoiGianNhanCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("time") String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.giaoCaByThoiGianNhanCa(basePageable(page, pageSize), simpleDateFormat.parse(time));
        System.out.println(time);
        return baseRepon(giaoCaResponsePage);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<?> giaoCaByIdAndStatus(@PathVariable("id") String id) {
        try {
            QuanLyGiaoCaResponse response = giaoCaAdminService.findGiaoCaByIdAndStatus(TrangThaiGiaoCa.KET_THUC_CA, id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Eror"));
        }
    }

    @GetMapping("sort-by-nhan-ca-asc")
    public ResponseEntity<?> sortByNhanCa(Optional<Integer> page, Optional<Integer> pageSize) {
        Sort sort = Sort.by("thoiGianNhanCa").ascending();
        Pageable pageable = PageRequest.of(page.orElse(0), pageSize.orElse(10), sort);
        return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(pageable, TrangThaiGiaoCa.KET_THUC_CA));
    }

    @GetMapping("sort-by-nhan-ca-desc")
    public ResponseEntity<?> sortByKetCa(Optional<Integer> page, Optional<Integer> pageSize) {
        Sort sort = Sort.by("thoiGianNhanCa").descending();
        Pageable pageable = PageRequest.of(page.orElse(0), pageSize.orElse(10), sort);
        return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(pageable, TrangThaiGiaoCa.KET_THUC_CA));
    }


    @GetMapping("export-excel")
    public ResponseEntity<?> exportExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String date = format.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=giao_ca_" + date + ".xlsx";

            response.setHeader(headerKey, headerValue);
            List<QuanLyGiaoCaResponse> list = giaoCaAdminService.findAllGiaoCaAndOrderByTimeNhanCa();
            GiaoCaExportExcel giaoCaExportExcel = new GiaoCaExportExcel(list);
            giaoCaExportExcel.export(response);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK,"Successfully exported"));
        }catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST,"Not exported"));
        }


    }
}
