package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountStaffRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountStaffService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/account")
public class CreateAccountStaffResController {

    @Autowired
    private AccountStaffService accountStaffService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getLoaiSan(Optional<Integer> size, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        Page<AccountStaffRespone> accountAdminResponses = accountStaffService.getAll(pageable);
        PageableObject<AccountStaffRespone> pageableObject = new PageableObject<AccountStaffRespone>(accountAdminResponses);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
    }

    @PostMapping("/create-staff")
    public ResponseEntity<?> createAccountStaff(@RequestBody @Valid AccountStaffResquest accountStaffResquest){
        try {
            accountStaffService.createrAccountStaff(accountStaffResquest);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "OK"));
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete( @PathVariable("id") String id){
        try {
            String result =accountStaffService.deleteAccountStaff(id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, result));
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

}
