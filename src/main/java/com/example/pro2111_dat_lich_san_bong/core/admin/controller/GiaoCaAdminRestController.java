package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.GiaoCaAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/giao-ca")
@CrossOrigin("*")
public class GiaoCaAdminRestController {

    @Autowired
    private GiaoCaAdminService giaoCaAdminService;


    private Pageable basePageable(Optional<Integer> page, Optional<Integer> pageSize, Sort sort) {
        return PageRequest.of(page.orElse(0), pageSize.orElse(10), sort);
    }

    private ResponseEntity<?> baseRepon(Page<QuanLyGiaoCaResponse> giaoCaResponsePage) {
        PageableObject<QuanLyGiaoCaResponse> giaoCaResponsePageableObject = new PageableObject<QuanLyGiaoCaResponse>(giaoCaResponsePage);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponsePageableObject));
    }

    @GetMapping("/owners")
    public ResponseEntity<?> ownersGiaoCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        try {
            if (sort.equals("asc")) {
                return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").ascending()), TrangThaiGiaoCa.KET_THUC_CA));
            }
            return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").descending()), TrangThaiGiaoCa.KET_THUC_CA));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Error"));
        }

    }

    @GetMapping("search")
    public ResponseEntity<?> searchGiaoCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("name") String name, @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        try {
            if (sort.equals("asc")) {
                Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.searchByName(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").ascending()), name);
                return baseRepon(giaoCaResponsePage);
            }
            Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.searchByName(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").descending()), name);
            return baseRepon(giaoCaResponsePage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST,"Error"));
        }

    }

    @GetMapping("rut-tien")
    public ResponseEntity<?> giaoCaCoTienRut(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        try {
            if (sort.equals("asc")) {
                return baseRepon(giaoCaAdminService.giaoCaCoTienRut(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").ascending())));
            }
            return baseRepon(giaoCaAdminService.giaoCaCoTienRut(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").descending())));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST,"Error"));
        }

    }

    @GetMapping("by-time")
    public ResponseEntity<?> giaoCaByThoiGianNhanCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("time") String time, @RequestParam(value = "sort", defaultValue = "desc") String sort) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (sort.equals("asc")) {
                Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.giaoCaByThoiGianNhanCa(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").ascending()), simpleDateFormat.parse(time));
                return baseRepon(giaoCaResponsePage);
            }
            Page<QuanLyGiaoCaResponse> giaoCaResponsePage = giaoCaAdminService.giaoCaByThoiGianNhanCa(basePageable(page, pageSize, Sort.by("thoiGianNhanCa").descending()), simpleDateFormat.parse(time));
            return baseRepon(giaoCaResponsePage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST,"Error"));
        }

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
        try {
            Sort sort = Sort.by("thoiGianNhanCa").ascending();
            Pageable pageable = PageRequest.of(page.orElse(0), pageSize.orElse(2), sort);
            return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(pageable, TrangThaiGiaoCa.KET_THUC_CA));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST,"Error"));
        }

    }

    @GetMapping("sort-by-nhan-ca-desc")
    public ResponseEntity<?> sortByKetCa(Optional<Integer> page, Optional<Integer> pageSize) {
        try {
            Sort sort = Sort.by("thoiGianNhanCa").descending();
            Pageable pageable = PageRequest.of(page.orElse(0), pageSize.orElse(2), sort);
            return baseRepon(giaoCaAdminService.findAllGiaoCaByStatus(pageable, TrangThaiGiaoCa.KET_THUC_CA));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST,"Error"));
        }

    }


    @GetMapping("export")
    public ResponseEntity<?> exportExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/octet-stream");
//            DateFormat format = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy");
//            String date = format.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=GiaoCa" + ".xlsx";

            response.setHeader(headerKey, headerValue);
            List<QuanLyGiaoCaResponse> list = giaoCaAdminService.findAllGiaoCaAndOrderByTimeNhanCa();
            giaoCaAdminService.exportExcel(response, list);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Successfully exported"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Not exported"));
        }


    }
}
