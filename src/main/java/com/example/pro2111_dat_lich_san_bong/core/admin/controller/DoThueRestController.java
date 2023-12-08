package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.NuocUongRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DoThueService;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.NuocUongService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.utils.UploadImg;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



@RestController
@RequestMapping("/api/v1/admin/do-thue")
@CrossOrigin("*")
@MultipartConfig
public class DoThueRestController {

    @Autowired
    private DoThueService doThueService;

    @Autowired
    private UploadImg uploadImg;

    String tenAnh = null;

    @GetMapping("/find-all")
    public BaseResponse<?> getNuocUong(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        try {
            Page<DoThueResponse> doThueResponses = doThueService.getDoThuePagaeble(page, size);
            PageableObject<DoThueResponse> pageableObject = new PageableObject<DoThueResponse>(doThueResponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, null);
        }

    }

    @GetMapping("/find/{id}")
    public BaseResponse<?> findById(@PathVariable("id") String id) {
        try {
            DoThue doThue = doThueService.findById(id);
            return new BaseResponse<>(HttpStatus.OK, doThue);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Error");
        }
    }

    @PostMapping(value = "/save")
    public BaseResponse<?> save(@RequestBody DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            doThueService.save(doThueRequest);
            return new BaseResponse<>(HttpStatus.OK, doThueRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PostMapping("/upload-image")
    public void loadImage(@RequestBody MultipartFile file) {
        try {
            String tb = uploadImg.uploadImg(file);
            System.out.println(tb);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        doThueService.update(doThueRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PostMapping("/import")
    public BaseResponse<?> importEcel(@RequestBody MultipartFile file) throws IOException {
        try {
            boolean check = doThueService.nuocUongImportExcel(file);
            if (!check) {
                return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
            }
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }

    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=DoThue" + "." + "xlsx";
            response.setHeader(headerKey, headerValue);
            doThueService.exprotExcel(response, doThueService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        try {
            doThueService.deleteById(id);
            return new BaseResponse<>(HttpStatus.OK, "Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid id");
        }
    }

    @GetMapping("find-by-name")
    public BaseResponse<?> findByName(@RequestParam("tenDoThue") String name, @RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        try {
            Page<DoThueResponse> doThueResponses = doThueService.findAllByName(page, size, name);
            PageableObject<DoThueResponse> pageableObject = new PageableObject<DoThueResponse>(doThueResponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        }
    }
}
