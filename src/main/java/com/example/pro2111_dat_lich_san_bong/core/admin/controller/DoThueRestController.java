package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.NuocUongRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DoThueService;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.NuocUongService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author caodinh
 */

@RestController
@RequestMapping("/api/v1/admin/do-thue")
public class DoThueRestController {

    @Autowired
    private DoThueService doThueService;

    @GetMapping("/get-do-THUE")
    public BaseResponse<?> getNuocUong(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
        return new BaseResponse<>(HttpStatus.OK, doThueService.getDoThuePagaeble(page, size).getContent());
    }

    @PostMapping("/save")
    public BaseResponse<?> save(@RequestBody DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        doThueService.save(doThueRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PutMapping("/update/{id}")
    public BaseResponse<?> update(@PathVariable("id") String id, @RequestBody DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        doThueService.updateById(id, doThueRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PostMapping("/import")
    public BaseResponse<?> importEcel(@RequestParam("file") MultipartFile file) throws IOException {
        doThueService.nuocUongImportExcel(file);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauImportExcelDoThue" + "." + "xlsx";
        response.setHeader(headerKey, headerValue);
        doThueService.exprotExcel(response);
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        doThueService.deleteById(id);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }
}
