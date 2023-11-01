package com.example.pro2111_dat_lich_san_bong.core.admin.controller;


import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LoaiSanAdminRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LuatSanRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LoaiSanAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LoaiSanAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/loai-san")
public class LoaiSanAdminRestController {

    @Autowired
    private LoaiSanAdminService loaiSanAdminService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getLuatSan() {
        List<LoaiSanAdminRespone> listLoaiSan = loaiSanAdminService.getAll();
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, listLoaiSan));
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<?> getLuatSan(@PathVariable("id") String id) {
        LoaiSanAdminRespone loaiSanAdminRespone = loaiSanAdminService.findById(id);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, loaiSanAdminRespone));
    }

    @PostMapping("/save")
    public BaseResponse<?> save(@RequestBody @Valid LoaiSanAdminRequets loaiSanAdminRequets)  {
        loaiSanAdminService.create(loaiSanAdminRequets);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }
}
