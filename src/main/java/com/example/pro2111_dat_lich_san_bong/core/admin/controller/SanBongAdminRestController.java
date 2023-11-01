package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminCreateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminUpdateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LoaiSanAdminService;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SanBongAdminService;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/san-bong")
public class SanBongAdminRestController {

    @Autowired
    private SanBongAdminService sanBongAdminService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getLoaiSan(Optional<Integer> size, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        Page<SanBongAdminRespone> sanBongAdminRespones = sanBongAdminService.getAll(pageable);
        PageableObject<SanBongAdminRespone> pageableObject = new PageableObject<SanBongAdminRespone>(sanBongAdminRespones);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
    }

    @PostMapping("/save")
    public BaseResponse<?> save(@RequestBody @Valid SanBongAdminCreateRequets sanBongAdminCreateRequets)  {
        try {
            sanBongAdminService.create(sanBongAdminCreateRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid SanBongAdminUpdateRequets sanBongAdminUpdateRequets)  {
        try {
            sanBongAdminService.update(sanBongAdminUpdateRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        try {
            sanBongAdminService.delete(id);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }
}
