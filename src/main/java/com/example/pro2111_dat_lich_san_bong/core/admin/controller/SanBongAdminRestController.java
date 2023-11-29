package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminCreateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.SanBongAdminUpdateRequets;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LoaiSanAdminService;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.SanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/san-bong")
@CrossOrigin("*")
public class SanBongAdminRestController {

    @Autowired
    private SanBongAdminService sanBongAdminService;

    private PageRequest pageRequest(Optional<Integer> size, Optional<Integer> page) {
        return PageRequest.of(page.orElse(0), size.orElse(5), Sort.by("tenSanBong").ascending());
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> getLoaiSan(@RequestParam("pageSize") Optional<Integer> size, @RequestParam("page") Optional<Integer> page) {
        try {
            Page<SanBongAdminRespone> sanBongAdminRespones = sanBongAdminService.getAll(pageRequest(size, page));
            PageableObject<SanBongAdminRespone> pageableObject = new PageableObject<SanBongAdminRespone>(sanBongAdminRespones);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> seachByName(@RequestParam("pageSize") Optional<Integer> size,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("name") String tenSanBong) {
        try {
            Page<SanBongAdminRespone> sanBongAdminRespones = sanBongAdminService.findAllByTenSanBongContains(tenSanBong, pageRequest(size, page));
            PageableObject<SanBongAdminRespone> pageableObject = new PageableObject<SanBongAdminRespone>(sanBongAdminRespones);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, pageableObject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }

    @PostMapping("/create")
    public BaseResponse<?> save(@RequestBody SanBongAdminCreateRequets sanBongAdminCreateRequets) {
        try {
            if (sanBongAdminService.findFirstByTenSanBong(sanBongAdminCreateRequets.getTenSanBong()) != null) {
                return new BaseResponse<>(HttpStatus.ACCEPTED, "Đã tồn tại");
            }
            sanBongAdminService.create(sanBongAdminCreateRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid SanBongAdminUpdateRequets sanBongAdminUpdateRequets) {
        try {
//            if (sanBongAdminService.findFirstByTenSanBong(sanBongAdminUpdateRequets.getTenSanBong()) != null) {
//                return new BaseResponse<>(HttpStatus.ACCEPTED, "Đã tồn tại");
//            }
            sanBongAdminService.update(sanBongAdminUpdateRequets);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        try {
            sanBongAdminService.delete(id);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            SanBongAdminRespone sanBongAdminRespone = sanBongAdminService.findByID(id);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, sanBongAdminRespone));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }
    }
}
