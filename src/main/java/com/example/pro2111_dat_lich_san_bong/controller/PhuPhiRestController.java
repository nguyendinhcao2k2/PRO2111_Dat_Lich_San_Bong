package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.ChucVuRequest;
import com.example.pro2111_dat_lich_san_bong.model.request.PhuPhiRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.PhuPhiService;
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
@RequestMapping("/api/admin/v1/phu-phi")
public class PhuPhiRestController {

    @Autowired
    private PhuPhiService phuPhiService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllPhuPhi() {
        List<PhuPhi> phuPhiList = phuPhiService.finAll();
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK,phuPhiList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findPhuPhiById(
            @PathVariable(name = "id") String id
    ) {
        PhuPhi phuPhi = phuPhiService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Phu Phi Width Id : " + id));
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, phuPhi), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deletePhuPhiById(
            @PathVariable(name = "id") String id
    ) {
        PhuPhi phuPhi = phuPhiService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Chuc Vu Width Id : " + id));
        phuPhiService.delete(phuPhi.getId());
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, "Delete Phu Phi Successfully"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> replacePhuPhi(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody PhuPhiRequest phuPhiRequest
    ) {
        PhuPhi phuPhi = phuPhiService.update(id, phuPhiRequest);
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, phuPhi), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createPhuPhi(
            @Valid @RequestBody PhuPhiRequest phuPhiRequest
    ) {
        return new ResponseEntity<>(
                new BaseResponse(
                        HttpStatus.CREATED, phuPhiService.create(phuPhiRequest)
                )
                ,HttpStatus.CREATED
        );
    }
}
