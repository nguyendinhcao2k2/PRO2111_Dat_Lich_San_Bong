package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LichSuViTienAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuViTienAdminService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/lich-su-vi-tien")
@CrossOrigin("*")
public class LichSuViTienAdminRestController {

    @Autowired
    private LichSuViTienAdminService lichSuViTienAdminService;

    private PageRequest pageRequest(Optional<Integer> size, Optional<Integer> page) {
        return PageRequest.of(page.orElse(1), size.orElse(5));
    }

//    @GetMapping("/find-all")
//    public ResponseEntity<?> getAll(Optional<Integer> size, Optional<Integer> page) {
//        try {
//            Page<LichSuViTienAdminRespone> lichSuViTienAdminRespones = lichSuViTienAdminService.getAll(pageRequest(size, page));
//            PageableObject<LichSuViTienAdminRespone> pageableObject = new PageableObject<LichSuViTienAdminRespone>(lichSuViTienAdminRespones);
//            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
////            List<LichSuViTienAdminRespone> lichSuViTienAdminRespones = lichSuViTienAdminService.listAll();
////            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, lichSuViTienAdminRespones));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Error"));
//        }
//    }

    @PostMapping("/create")
    public BaseResponse<?> save(@RequestBody @Valid LichSuViTienAdminRequest lichSuViTienAdminRequest) {
        try {
            lichSuViTienAdminService.create(lichSuViTienAdminRequest);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid LichSuViTienAdminRequest lichSuViTienAdminRequest ) {
        try {
            lichSuViTienAdminService.update(lichSuViTienAdminRequest);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        try {
            lichSuViTienAdminService.delete(id);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }
}
