package com.example.pro2111_dat_lich_san_bong.core.admin.controller;


import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LuatSanRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LuatSanAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/admin/luat-san")
@CrossOrigin("*")
public class LuatSanAdminRestController {

    @Autowired
    private LuatSanAdminService luatSanAdminService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getLuatSan() {
        List<LuatSanResponse> luatSanResponse = luatSanAdminService.getAll();
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, luatSanResponse));
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        LuatSanResponse luatSanResponse = luatSanAdminService.findById(id);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, luatSanResponse));
    }

    @PostMapping("/create")
    public BaseResponse<?> save(@RequestBody LuatSanRequest luatSanRequest) {
        luatSanAdminService.createLuatSan(luatSanRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody  LuatSanRequest luatSanRequest) {
        luatSanAdminService.update(luatSanRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        luatSanAdminService.delete(id);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

}
