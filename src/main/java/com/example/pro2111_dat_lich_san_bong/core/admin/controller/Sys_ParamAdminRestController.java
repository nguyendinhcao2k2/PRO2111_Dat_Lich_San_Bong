package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminCreateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SysParamAdminUpdateRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SysParamAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SysParamAdminService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sys-param")
@CrossOrigin("*")
public class Sys_ParamAdminRestController {

    @Autowired
    private SysParamAdminService sysParamAdminService;

    @GetMapping("list-code")
    public ResponseEntity<?> getListCode() {
        try {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, SYSParamCodeConstant.LIST_PARAM_CODES));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> getALl() {
        try {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, sysParamAdminService.getAll()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> findByID(@PathVariable("id") String id) {
        try {
            SysParamAdminResponse response = sysParamAdminService.findByID(id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSysParam(@RequestBody SysParamAdminCreateRequest createRequest) {
        try {
            if (createRequest.getCode().equals(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC)) {
                SysParamAdminResponse sysParamAdminResponse = sysParamAdminService.findFirstByCode(createRequest.getCode());
                if (sysParamAdminResponse != null) {
                    return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ACCEPTED, "Đã tồn tại"));
                }
            }
            createRequest.setName(createRequest.getChucNang());
            sysParamAdminService.save(createRequest);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, createRequest));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSysParam(@RequestBody SysParamAdminUpdateRequest updateRequest) {
        try {
            sysParamAdminService.update(updateRequest);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, updateRequest));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSysParam(@PathVariable("id") String id) {
        try {
            sysParamAdminService.deleteById(id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }


}
