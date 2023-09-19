package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.DoThueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api-v1-admin/do-thue")
public class DoThueController {
    @Autowired
    private DoThueService doThueService;

    @GetMapping("/all")
    private ResponseEntity<BaseResponse> findAll(){
        System.out.println(doThueService.getAll());
        List<DoThue> listDoThue = doThueService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, listDoThue));
    }

    @GetMapping("/{id}")
    private ResponseEntity<BaseResponse> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, doThueService.findById(id)));
    }


    @PostMapping("/create")
    private ResponseEntity<BaseResponse> create(@Valid @RequestBody DoThueRequest doThueRequest){
        doThueService.create(doThueRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Create Ok"));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<BaseResponse> remove(@PathVariable String id){
        doThueService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Delete OK"));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<BaseResponse> update(@PathVariable String id, @Valid  @RequestBody DoThueRequest doThueRequest) {
        doThueService.update(id, doThueRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Update OK"));
    }
}
