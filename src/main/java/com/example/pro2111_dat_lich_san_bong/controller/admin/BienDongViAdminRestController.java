package com.example.pro2111_dat_lich_san_bong.controller.admin;

import com.example.pro2111_dat_lich_san_bong.entity.BienDongVi;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.BienDongViRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.BienDongViService;
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
@RequestMapping("/api/v1/admin/bien-dong-vi")
public class BienDongViAdminRestController {

    @Autowired
    private BienDongViService bienDongViService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllChucVu() {
        List<BienDongVi> bienDongViLists = bienDongViService.finAll();
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, bienDongViLists), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findBienDongViById(
            @PathVariable(name = "id") String id
    ) {
        BienDongVi bienDongVi = bienDongViService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Bien Dong Vi Width Id : " + id));
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, bienDongVi), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteBienDongViById(
            @PathVariable(name = "id") String id
    ) {
        BienDongVi bienDongVi = bienDongViService.findById(id)
                .orElseThrow(() -> new RestApiException("Not Found Bien Dong Vi Width Id : " + id));
        bienDongViService.delete(bienDongVi.getId());
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, "Delete Bien Dong Vi Successfully"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> replaceBienDongVi(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody BienDongViRequest bienDongViRequest
    ) {
        BienDongVi bienDongVi = bienDongViService.update(id, bienDongViRequest);
        return new ResponseEntity<>(new BaseResponse(HttpStatus.OK, bienDongVi), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createBienDongVi(
            @Valid @RequestBody BienDongViRequest bienDongViRequest
    ) {
        return new ResponseEntity<>(
                new BaseResponse(
                        HttpStatus.CREATED, bienDongViService.create(bienDongViRequest)
                )
                , HttpStatus.CREATED
        );
    }

}
