package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HoaDonSanCaViewRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IThanhToanSanCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/staff/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private IThanhToanSanCaStaffService iThanhToanSanCaStaffService;


        @GetMapping("/generate/{id}")
        public ResponseEntity<byte[]> generatePdf(@PathVariable("id") String id) throws IOException {
            HoaDonThanhToanRequest hoaDonThanhToanRequest = iThanhToanSanCaStaffService.getOneHoaDonThanhToan(id);
            byte[] pdfBytes = invoiceService.generatePdf(hoaDonThanhToanRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hoa_don.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }
    }


