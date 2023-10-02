package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.GiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.request.GiaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.service.IGiaoCaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/static/api/v1/staff/giao-ca")
public class GiaoCaRestController {

    @Autowired
    private IGiaoCaService giaoCaService;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping("/trang-thai-ca")
    public ResponseEntity<BaseResponse<GiaoCa>> trangThaiGiaoCa(@RequestParam("trangThaiGiaoCa") Integer trangThaiGiaoCa) {
        GiaoCa giaoCaCoNhanVien = giaoCaService.findGiaoCaByTrangThai(trangThaiGiaoCa);
        BaseResponse<GiaoCa> response = new BaseResponse<>();
        response.setContent(giaoCaCoNhanVien);
        response.setStatusCode(HttpStatus.OK);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/khoi-tao-ca")
    public ResponseEntity<?> khoiTaoCaLam(@RequestBody GiaoCaRequest giaoCaRequest) {
        GiaoCa giaoCa = mapper.map(giaoCaRequest, GiaoCa.class);
        Boolean check = giaoCaService.khoiTaoCaLam(giaoCa);
        if (check) {
            return ResponseEntity.ok(new BaseResponse<GiaoCaRequest>(HttpStatus.OK, giaoCaRequest));
        }
        return ResponseEntity.ok("Khởi tạo ca làm lỗi!");
    }

    @GetMapping("/ca-gan-nhat")
    public ResponseEntity<?> giaoCaGanNhat() {
        GiaoCa giaoCa = giaoCaService.findGiaoCaGanNhat();
        if (giaoCa != null) {
            BaseResponse<GiaoCa> response = new BaseResponse<>();
            response.setStatusCode(HttpStatus.OK);
            response.setContent(giaoCa);
            return ResponseEntity.ok(response);
        }
        return null;

    }

}
