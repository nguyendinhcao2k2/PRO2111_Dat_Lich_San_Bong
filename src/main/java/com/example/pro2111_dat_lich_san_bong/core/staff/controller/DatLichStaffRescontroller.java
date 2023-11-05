package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
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

import java.util.List;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/v1/staff")
public class DatLichStaffRescontroller {

    @Autowired
    private IDatSanStaffService iDatSanStaffService;

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
    public BaseResponse<String> datLich(@Valid @RequestBody ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
        if (!iDatSanStaffService.datLich(thongTinNguoiDatRequest)) {
            throw new RestApiException("Có lỗi !");
        }
        return new BaseResponse<>(HttpStatus.OK, "Đặt lịch thành công");
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
