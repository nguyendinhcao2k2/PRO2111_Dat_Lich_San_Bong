package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.MaGiamGia;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.MaGiamGiaRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.MaGiamGiaService;
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
@RequestMapping("/api-v1-admin/ma-giam-gia")
public class MaGiamGiaRestController {

    @Autowired
    private MaGiamGiaService maGiamGiaService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllMaGiamGia() {
        List<MaGiamGia> maGiamGiaLists = maGiamGiaService.finAll();
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, maGiamGiaLists), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findMaGiamGiaById(
            @PathVariable(name = "id") String id
    ) {
        MaGiamGia maGiamGia = maGiamGiaService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Ma Giam Gia Width Id : " + id));
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, maGiamGia), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteMaGiamGiaById(
            @PathVariable(name = "id") String id
    ) {
        MaGiamGia maGiamGia = maGiamGiaService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Ma Giam Gia Width Id : " + id));
        maGiamGiaService.delete(maGiamGia.getId());
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, "Delete Ma Giam Gia Successfully"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> replaceMaGiamGia(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody MaGiamGiaRequest maGiamGiaRequest
    ) {
        MaGiamGia maGiamGia = maGiamGiaService.update(id, maGiamGiaRequest);
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, maGiamGia), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createMaGiamGia(
            @Valid @RequestBody MaGiamGiaRequest maGiamGiaRequest
    ) {
        return new ResponseEntity<>(
                new BaseResponse(
                        HttpStatus.CREATED, maGiamGiaService.create(maGiamGiaRequest)
                )
                ,HttpStatus.CREATED
        );
    }
}
