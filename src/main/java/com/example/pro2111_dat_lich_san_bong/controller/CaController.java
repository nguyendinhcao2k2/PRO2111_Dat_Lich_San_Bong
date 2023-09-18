package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.model.request.CaRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.CaService;
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
@RequestMapping("/api/admin/v1/ca")
public class CaController {

    @Autowired
    private CaService caService;

    @GetMapping("/all")
    private ResponseEntity<BaseResponse> findAll(){
        System.out.println(caService.getAll());
        List<Ca> listCa = caService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, listCa));
        }

    @GetMapping("/{id}")
    private ResponseEntity<BaseResponse> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, caService.findById(id)));
    }


    @PostMapping("/create")
    private ResponseEntity<BaseResponse> create(@Valid @RequestBody CaRequest caRequest){
        caService.create(caRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Create Ok"));
        }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<BaseResponse> remove(@PathVariable String id){
        caService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Delete OK"));
        }

    @PutMapping("/update/{id}")
    private ResponseEntity<BaseResponse> update(@PathVariable String id, @Valid  @RequestBody CaRequest caRequest) {
        caService.update(id, caRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Update OK"));
    }
}
