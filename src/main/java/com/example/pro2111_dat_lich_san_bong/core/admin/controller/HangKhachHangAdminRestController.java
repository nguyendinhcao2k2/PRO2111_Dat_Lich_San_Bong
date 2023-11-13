package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DichVuSanBongAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HangKhachHangAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DichVuSanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.HangKhachHangAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DichVuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HangKhachHangAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/hang-khach-hang")
@CrossOrigin("*")
public class HangKhachHangAdminRestController {

    @Autowired
    private HangKhachHangAdminService hangKhachHangAdminService;

    private PageRequest pageRequest(Optional<Integer> size, Optional<Integer> page) {
        return PageRequest.of(page.orElse(0), size.orElse(5));
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> getAll(Optional<Integer> size, Optional<Integer> page) {
        try {
            Page<HangKhachHangAdminRespone> hangKhachHangAdminRespones = hangKhachHangAdminService.getAll(pageRequest(size, page));
            PageableObject<HangKhachHangAdminRespone> pageableObject = new PageableObject<HangKhachHangAdminRespone>(hangKhachHangAdminRespones);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @PostMapping("/create")
    public BaseResponse<?> save(@RequestBody @Valid HangKhachHangAdminRequets hangKhachHangAdminRequets) {
        try {
            hangKhachHangAdminService.create(hangKhachHangAdminRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid HangKhachHangAdminRequets hangKhachHangAdminRequets) {
        try {
            hangKhachHangAdminService.update(hangKhachHangAdminRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        try {
            hangKhachHangAdminService.delete(id);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }
}
