package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.NuocUongResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.NuocUongService;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.NuocUongRequest;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1/admin/nuoc-uong")
public class NuocUongRestController {

    @Autowired
    private NuocUongService nuocUongService;

    @GetMapping("/get-nuoc-uong")
    public BaseResponse<?> getNuocUong(@RequestParam(value = "size",defaultValue = "10") Integer size, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        Page<NuocUongResponse> nuocUongResponsePage = nuocUongService.getNuocUongNyPagaeble(page, size);
        PageableObject<NuocUongResponse> pageableObject = new PageableObject<NuocUongResponse>(nuocUongResponsePage);
        return new BaseResponse<>(HttpStatus.OK, pageableObject);
    }

    @PostMapping("/save")
    public BaseResponse<?> save(@RequestBody NuocUongRequest nuocUongRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        nuocUongService.save(nuocUongRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PutMapping("/update/{id}")
    public BaseResponse<?> update(@PathVariable("id") String id, @RequestBody NuocUongRequest nuocUongRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        nuocUongService.updateById(id, nuocUongRequest);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @PostMapping("/import")
    public BaseResponse<?> importEcel(@RequestParam("file") MultipartFile file) throws IOException {
        nuocUongService.nuocUongImportExcel(file);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauImportExcelNuocUong" + "." + "xlsx";
        response.setHeader(headerKey, headerValue);
        nuocUongService.exprotExcel(response);
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> delete(@PathVariable("id") String id) {
        nuocUongService.deleteById(id);
        return new BaseResponse<>(HttpStatus.OK, "Ok");
    }

}
