package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HangKhachHang;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuChat;
import com.example.pro2111_dat_lich_san_bong.model.request.HangKhachHangRequest;
import com.example.pro2111_dat_lich_san_bong.model.request.LichSuChatRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.HangKhachHangService;
import com.example.pro2111_dat_lich_san_bong.service.LichSuChatService;
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
@RequestMapping("/api/admin/v1/lich-su-chat")
public class LichSuChatController {

    @Autowired
    private LichSuChatService lichSuChatService;

    @GetMapping("/all")
    private ResponseEntity<BaseResponse> findAll(){
        List<LichSuChat> getAll = lichSuChatService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, getAll));
    }

    @GetMapping("/{id}")
    private ResponseEntity<BaseResponse> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, lichSuChatService.findById(id)));
    }


    @PostMapping("/create")
    private ResponseEntity<BaseResponse> create(@Valid @RequestBody LichSuChatRequest lichSuChatRequest){
        lichSuChatService.create(lichSuChatRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Create Ok"));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<BaseResponse> remove(@PathVariable String id){
        lichSuChatService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Delete OK"));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<BaseResponse> update(@PathVariable String id, @Valid  @RequestBody LichSuChatRequest lichSuChatRequest) {
        lichSuChatService.update(id, lichSuChatRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(HttpStatus.OK, "Update OK"));
    }
}
