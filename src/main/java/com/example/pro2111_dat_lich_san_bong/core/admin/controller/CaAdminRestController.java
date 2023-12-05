package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.excel.CaExportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.excel.CaImportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CaAdminRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.CheckTimeListReQuest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.CaAdminReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.CaAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/ca")
public class CaAdminRestController {

    @Autowired
    private CaAdminService caAdminService;

    @GetMapping("find-all")
    public BaseResponse<?> getAllCa(@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page) {
        try {
            Page<CaAdminReponse> caAdminReponses = caAdminService.findAllCa(size.orElse(10), page.orElse(0));
            PageableObject<CaAdminReponse> pageableObject = new PageableObject<>(caAdminReponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("search")
    public BaseResponse<?> searchByName(@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page, @RequestParam("name") String tenCa) {
        try {
            Page<CaAdminReponse> caAdminReponses = caAdminService.findByName(size.orElse(10), page.orElse(0), tenCa);
            PageableObject<CaAdminReponse> pageableObject = new PageableObject<>(caAdminReponses);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("find/{id}")
    public BaseResponse<?> findById(@PathVariable("id") String id) {
        try {
            Ca ca = caAdminService.findById(id);
            return new BaseResponse<>(HttpStatus.OK, ca);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid");
        }
    }

    @PostMapping("create")
    public BaseResponse<?> createCa(@RequestBody CaAdminRequest caAdminRequest) {
        try {
            boolean check = checkThoiGianCa(caAdminRequest.getThoiGianBatDau(), caAdminRequest.getThoiGianKetThuc());
            if (!check) {
                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
            }
            caAdminService.saveOrUpdate(caAdminRequest);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    private boolean checkThoiGianCa(Time thoiGianBatDauNew, Time thoiGianKetThucNew) {
        List<Ca> caList = caAdminService.findAllListCa();
        for (Ca ca : caList) {
            //neu thoi gian bat dau nam trong khoang thoi gian ca nào đó thì là false
            if (thoiGianBatDauNew.after(ca.getThoiGianBatDau()) && thoiGianBatDauNew.before(ca.getThoiGianKetThuc())) {
                return false;
            }
            //neu thoi gian ket thuc nam trong khoang thoi gian ca nào đó thì là false
            if (thoiGianKetThucNew.after(ca.getThoiGianBatDau()) && thoiGianKetThucNew.before(ca.getThoiGianKetThuc())) {
                return false;
            }
            //neu thoi gian bat dau be hon thoi gian bat dau cu và thoi gian ket thuc lơn hon thoi gian ket thuc cu => false
            if (thoiGianBatDauNew.before(ca.getThoiGianBatDau()) && thoiGianKetThucNew.after(ca.getThoiGianKetThuc())) {
                return false;
            }
        }
        return true;
    }

    @PutMapping("update")
    public BaseResponse<?> updateCa(@RequestBody CaAdminRequest caAdminRequest) {
        try {
            Ca caCu = caAdminService.findById(caAdminRequest.getId());
            List<Ca> caList = caAdminService.findAllListCa();
            for (int i = 0; i < caList.size(); i++) {
                Time thoiGianBatDauHT = caList.get(i).getThoiGianBatDau();
                Time thoiGianKetThucHT = caList.get(i).getThoiGianKetThuc();

                // neu ca moi nam trong khoang ca cu thì cho update
                if (caAdminRequest.getThoiGianBatDau().equals(caCu.getThoiGianBatDau()) || caAdminRequest.getThoiGianBatDau().after(caCu.getThoiGianBatDau())) {
                    if (caAdminRequest.getThoiGianKetThuc().equals(caCu.getThoiGianKetThuc()) || caAdminRequest.getThoiGianKetThuc().before(caCu.getThoiGianKetThuc())) {
                        caAdminService.saveOrUpdate(caAdminRequest);
                        return new BaseResponse<>(HttpStatus.OK, "Successfully");
                    }
                }
                if (caAdminRequest.getThoiGianBatDau().equals(caCu.getThoiGianBatDau())) {
                    if (caAdminRequest.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        if (!caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)) {
                            if (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) &&  caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT)) {
                                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                            }
                        }
                    }
                } else if (caAdminRequest.getThoiGianKetThuc().equals(caCu.getThoiGianKetThuc())) {
                    if (caAdminRequest.getThoiGianBatDau().before(caCu.getThoiGianBatDau())) {
                        if (!caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)) {
                            if (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) &&  caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT)) {
                                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                            }
                        }
                    }
                } else {
                    if (caAdminRequest.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && caAdminRequest.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        if (!caCu.getThoiGianBatDau().equals(thoiGianBatDauHT) && !caCu.getThoiGianKetThuc().equals(thoiGianKetThucHT)) {
                            if (caAdminRequest.getThoiGianBatDau().after(thoiGianBatDauHT) || caAdminRequest.getThoiGianBatDau().before(thoiGianBatDauHT) &&  caAdminRequest.getThoiGianKetThuc().before(thoiGianKetThucHT) || caAdminRequest.getThoiGianKetThuc().after(thoiGianKetThucHT)) {
                                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Ca đã tồn tại");
                            }
                        }
                    }
                }

            }

            caAdminService.saveOrUpdate(caAdminRequest);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");


        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @DeleteMapping("delete/{id}")
    public BaseResponse<?> deleteById(@PathVariable("id") String id) {
        try {
            caAdminService.deleteById(id);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @GetMapping("/export")
    public BaseResponse<?> export(HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Ca" + "." + "xlsx";
            response.setHeader(headerKey, headerValue);
            CaExportExcel.exportData(response, caAdminService.findAllListCa());
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @PostMapping("/import")
    public BaseResponse<?> importEcel(@RequestBody MultipartFile file) throws IOException {
        try {
            Set<CheckTimeListReQuest> listCheck = new HashSet<>();
            List<Ca> caList = CaImportExcel.readExcel(file);
            List<Ca> findAllCaList = caAdminService.findAllListCa();

            for (Ca caCheck : caList) {
                for (Ca caCu : findAllCaList) {
                    if (!caCheck.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && !caCheck.getThoiGianBatDau().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                    if (!caCheck.getThoiGianKetThuc().before(caCu.getThoiGianBatDau()) && !caCheck.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                    if (caCheck.getThoiGianBatDau().before(caCu.getThoiGianBatDau()) && caCheck.getThoiGianKetThuc().after(caCu.getThoiGianKetThuc())) {
                        listCheck.add(new CheckTimeListReQuest(caCheck.getThoiGianBatDau(), caCheck.getThoiGianKetThuc()));
                    }
                }
            }

            if (listCheck.size() > 0) {
                return new BaseResponse<>(HttpStatus.ALREADY_REPORTED, listCheck);
            }

            caAdminService.saveAll(caList);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }

    }
}
