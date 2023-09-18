package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HangKhachHang;
import com.example.pro2111_dat_lich_san_bong.model.request.HangKhachHangRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.HangKhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/api/admin/v1/hang-khach-hang")
public class HangKhachHangController {

    @Autowired
    private HangKhachHangService hangKhachHangService;

    @GetMapping("/all")
    private ResponseEntity<BaseResponse> findAll(){
        List<HangKhachHang> getAll = hangKhachHangService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, getAll));
    }

    @GetMapping("/{id}")
    private ResponseEntity<BaseResponse> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, hangKhachHangService.findById(id)));
    }


    @PostMapping("/create")
    private ResponseEntity<BaseResponse> create(@Valid @RequestBody HangKhachHangRequest hangKhachHangRequest){
        hangKhachHangService.create(hangKhachHangRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Create Ok"));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<BaseResponse> remove(@PathVariable String id){
        hangKhachHangService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Delete OK"));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<BaseResponse> update(@PathVariable String id, @Valid  @RequestBody HangKhachHangRequest hangKhachHangRequest) {
        hangKhachHangService.update(id, hangKhachHangRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Update OK"));
    }
}
