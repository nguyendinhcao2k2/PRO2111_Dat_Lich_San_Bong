package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountStaffRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountAdminStaffService;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/account")
@CrossOrigin("*")
public class AccountAdminStaffRestController {

    @Autowired
    private AccountAdminStaffService accountAdminStaffService;

    private PageRequest pageRequest(Optional<Integer> page,Optional<Integer> pageSize){
        return PageRequest.of(page.orElse(0), pageSize.orElse(5));
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize) {
        try {
            Pageable pageable = pageRequest(page,pageSize);
            Page<AccountStaffRespone> staffRespones = accountAdminStaffService.getAll(pageable);
            PageableObject<AccountStaffRespone> pageableObject = new PageableObject<>(staffRespones);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, pageableObject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSDT(@RequestParam("sdt") String sdt, @RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize) {
        try {
            Pageable pageable = pageRequest(page,pageSize);
            Page<AccountStaffRespone> staffRespones = accountAdminStaffService.findAccountBySoDienThoai(sdt, pageable);
            PageableObject<AccountStaffRespone> pageableObject = new PageableObject<>(staffRespones);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, pageableObject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccountStaff(@RequestBody @Valid AccountStaffResquest accountStaffResquest) {
        try {
            if (accountAdminStaffService.findFirstByEmailAAndTaiKhoan(accountStaffResquest.getTaiKhoan()) == null) {
                accountAdminStaffService.createrAccountStaff(accountStaffResquest);
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, accountStaffResquest));
            }
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ACCEPTED, "Đã tồn tại"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            String result = accountAdminStaffService.deleteAccountStaff(id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, result));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

}
