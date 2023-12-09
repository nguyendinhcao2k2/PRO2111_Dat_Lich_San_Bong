package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/v1/staff")
public class DatLichStaffRescontroller {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpSession session;

    @Autowired
    private IDatSanStaffService iDatSanStaffService;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @GetMapping("/load-san-bong")
    public ResponseEntity<List<LoadSanBongRespose>> loadSanBong() {
        return ResponseEntity.ok(iDatSanStaffService.loadSanBong());
    }

    @PostMapping("/filter-lich-dat")
    public ResponseEntity<List<HoaDonStaffResponse>> filterLichDat(@RequestBody FilterLichDatRequest filterLichDatRequest) {
        return ResponseEntity.ok(iDatSanStaffService.filterHoaDon(filterLichDatRequest));
    }

    @PostMapping("/search-san-bong")
    public ResponseEntity<List<LoadSanBongRespose>> search(@RequestBody FilterSanBongRequest filterSanBongRequest) {
        return ResponseEntity.ok(iDatSanStaffService.filterSanBong(filterSanBongRequest));
    }

    @PostMapping("/dat-lich")
    public BaseResponse<String> datLich(@Valid @RequestBody ThongTinNguoiDatRequest thongTinNguoiDatRequest, HttpServletRequest request) {
        if (!iDatSanStaffService.datLich(thongTinNguoiDatRequest,request)) {
            throw new RestApiException("Có lỗi !");
        }
        return new BaseResponse<>(HttpStatus.OK, "Đặt lịch thành công");
    }

    @PostMapping("/submit-order")
    public String submidOrder(@RequestBody ThongTinNguoiDatRequest thongTinNguoiDatRequest, HttpServletRequest request) {
        Integer price = 0;
        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinNguoiDatRequest.getThongTinLichDatRequests()) {
            price += Integer.valueOf(thongTinLichDatRequest.getPrice());
        }
        session.setAttribute("thongTinNguoiDat", thongTinNguoiDatRequest);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/staff/return-payment";
        String vnpayUrl = vnPayService.createOrder((int) (price * 0.5), thongTinNguoiDatRequest.getHoVaTen(), baseUrl,30);
        return vnpayUrl;
    }

    @GetMapping("/show-danh-sach-cho")
    public ResponseEntity<List<HoaDonStaffResponse>> showDanhSachCho() {
        return ResponseEntity.ok(iDatSanStaffService.getHoaDonByTrangThai());
    }

    @DeleteMapping("/delete-hoa-don")
    public BaseResponse<String> deleteHoaDon(@RequestParam("idHoaDon") String idHoaDon) {
        iDatSanStaffService.huySanBong(idHoaDon);
        return new BaseResponse<>(HttpStatus.OK, "Hủy thành công");
    }
}
