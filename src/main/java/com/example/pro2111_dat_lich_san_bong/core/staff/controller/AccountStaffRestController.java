package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff/account")
public class AccountStaffRestController {

    @Autowired
    private IAccountStaffService _accountStaffService;

    @Autowired
    private CommonSession commonSession;

    @GetMapping
    public ResponseEntity<?> getAccountByLogin() {
        try {
            AccountResponse accountResponse = _accountStaffService.findAccountById(commonSession.getUserId());
            return ResponseEntity.ok(new BaseResponse<AccountResponse>(HttpStatus.OK, accountResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("find-by-roles")
    public ResponseEntity<?> findListByRoles() {
        try {
            List<AccountResponse> list = _accountStaffService.getAccountByChucVu("ROLE_STAFF");
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, list));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<?> findAccountById(@PathVariable("id") String id) {
        try {
            System.out.println("Helloooooooooooo:" + id);
            AccountResponse accountResponse = _accountStaffService.findAccountById(id);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, accountResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
