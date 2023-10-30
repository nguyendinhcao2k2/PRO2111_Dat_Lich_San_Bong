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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/admin/luat-san")
public class LuatSanAdminRestController {

    @Autowired
    private LuatSanAdminService luatSanAdminService;

    @GetMapping("/get-luat-san")
    public ResponseEntity<?> getLuatSan(Optional<Integer>  size, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(1), size.orElse(5));
        Page<LuatSanResponse> luatSanResponse = luatSanAdminService.getAll(pageable);
        PageableObject<LuatSanResponse> pageableObject = new PageableObject<LuatSanResponse>(luatSanResponse);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
    }

    @PostMapping("/save")
    public BaseResponse<?> save(@RequestBody @Valid LuatSanRequest luatSanRequest)  {
        luatSanAdminService.createLuatSan(luatSanRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid LuatSanRequest luatSanRequest)  {
        luatSanAdminService.update(luatSanRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        luatSanAdminService.delete(id);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

}
