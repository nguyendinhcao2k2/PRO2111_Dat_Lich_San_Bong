package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.ChucVuRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.ChucVuService;
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

@RestController
@RequestMapping("/api/admin/v1/chuc-vu")
public class ChucVuRestController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllChucVu() {
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, chucVuService.finAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findChucVuById(
            @PathVariable(name = "id") String id
    ) {
        ChucVu chucVu = chucVuService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Chuc Vu Width Id : " + id));
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, chucVu), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteChucVuById(
            @PathVariable(name = "id") String id
    ) {
        ChucVu chucVu = chucVuService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Chuc Vu Width Id : " + id));
        chucVuService.delete(chucVu.getId());
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, "Delete Chuc Vu Successfully"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> replaceChucVu(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody ChucVuRequest chucVuRequest
    ) {
        ChucVu chucVu = chucVuService.update(id, chucVuRequest);
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, chucVu), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createChucVu(
            @Valid @RequestBody ChucVuRequest chucVuRequest
    ) {
        return new ResponseEntity<>(
                new BaseResponse(
                        HttpStatus.CREATED, chucVuService.create(chucVuRequest)
                )
                ,HttpStatus.CREATED
        );
    }
}
