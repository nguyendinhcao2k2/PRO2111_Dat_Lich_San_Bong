package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KetCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.currency.CurrencyConfig;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/staff/giao-ca")
public class GiaoCaStaffRestController {

    @Autowired
    private IGiaoCaStaffService _giaoCaStaffService;

    @Autowired
    private CommonSession _commonSession;

    @Autowired
    private IAccountStaffService accountStaffService;

    @Autowired
    private CurrencyConfig currencyConfig;

    @GetMapping
    public ResponseEntity<?> getGiaoCaStaff() {
        try {
            GiaoCaStaffResponse giaoCaStaffResponse = new GiaoCaStaffResponse();

            List<AccountResponse> accountResponseList = accountStaffService.getAccountByChucVu("ROLE_STAFF");
            giaoCaStaffResponse.setAccountResponseList(accountResponseList);
            GiaoCaResponse giaoCaResponse = _giaoCaStaffService.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);
            giaoCaStaffResponse.setId(giaoCaResponse.getId());
            giaoCaStaffResponse.setThoiGianNhanCa(giaoCaResponse.getThoiGianNhanCa());
            giaoCaStaffResponse.setThoiGianHienTai(new Timestamp(new Date().getTime()));
            giaoCaStaffResponse.setTienBanDau(giaoCaResponse.getTienBanDau());
            AccountResponse accountResponse = accountStaffService.findAccountById(giaoCaResponse.getIdNhanVienTrongCa());
            giaoCaStaffResponse.setAccountResponse(accountResponse);
            //tong so hoa don da thanh toan trong ca
            Integer countHDTT = _giaoCaStaffService.tongSoHoaDonTrongCaFollowStatus(0, 0);
            if (countHDTT == null) {
                countHDTT = 0;
            }
            giaoCaStaffResponse.setTongHoaDonDaThanhToan(countHDTT);
            //tong so hoa don chua thanh toan trong ca
            Integer countHDCTT = _giaoCaStaffService.tongSoHoaDonTrongCaFollowStatus(0, 1);
            if (countHDCTT == null) {
                countHDCTT = 0;
            }
            giaoCaStaffResponse.setTongHoaDonChuaThanhToan(countHDCTT);
            //tong tien bang tien mat
            Double totalCash = _giaoCaStaffService.tongTienTrongCaTheoHinhThucThanhToan(0);
            if (totalCash == null) {
                totalCash = 0.0;
            }
            giaoCaStaffResponse.setTongTientThanhToanBangTienMat(totalCash);
            //tong tien bang chuyen khoan
            Double totalTransfer = _giaoCaStaffService.tongTienTrongCaTheoHinhThucThanhToan(1);
            if (totalTransfer == null) {
                totalTransfer = 0.0;
            }
            giaoCaStaffResponse.setTongTientThanhToanBangChuyenKhoan(totalTransfer);
            //tong tien mat trong ca
            giaoCaStaffResponse.setTongTienMatTrongCa(totalCash + giaoCaStaffResponse.getTienBanDau());
            //tong tien thu trong ca
            Double total = Double.valueOf(totalCash) + Double.valueOf(totalTransfer);
            giaoCaStaffResponse.setTongTienThuTrongCa(total);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaStaffResponse));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, "Service Error"));
        }

    }

    @PostMapping("khoi-tao-ca-lam")
    public ResponseEntity<?> khoiTaoCaLam(@RequestBody KhoiTaoCaRequest khoiTaoCaRequest) {
        var check = _giaoCaStaffService.khoiTaoCaLamViec(khoiTaoCaRequest);
        if (check) {
            return ResponseEntity.ok(new BaseResponse<KhoiTaoCaRequest>(HttpStatus.OK, khoiTaoCaRequest));
        }
        return ResponseEntity.ok(new BaseResponse<String>(HttpStatus.BAD_GATEWAY, "Nhận ca thất bại!"));
    }

    @GetMapping("order-by-thoi-gian-nhan-ca")
    public ResponseEntity<?> orderByThoiGinaNhanCa() {
        try {
            GiaoCaResponse giaoCaResponse = _giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("by-status-account")
    public ResponseEntity<?> findGiaoCaByTrangThaiAndIdAccount() {
        try {
            GiaoCaResponse giaoCaResponse = _giaoCaStaffService.findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa.NHAN_CA, _commonSession.getUserId());
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("ket-ca")
    public ResponseEntity<?> ketCa(@RequestBody KetCaRequest ketCaRequest) {
        try {
            boolean check = _giaoCaStaffService.ketCa(ketCaRequest);
            if (check) {
                return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, ketCaRequest));
            }
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.NOT_ACCEPTABLE, ketCaRequest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không đáp ứng yêu cầu!");
        }
    }

    private Pageable basePageable(Optional<Integer> page, Optional<Integer> pageSize) {
        return PageRequest.of(page.orElse(0), pageSize.orElse(1));
    }

    private ResponseEntity<?> baseRepon(Page<QuanLyGiaoCaResponse> giaoCaResponsePage) {
        PageableObject<QuanLyGiaoCaResponse> giaoCaResponsePageableObject = new PageableObject<QuanLyGiaoCaResponse>(giaoCaResponsePage);
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponsePageableObject));
    }

    @GetMapping("/owners")
    public ResponseEntity<?> ownersGiaoCa(Optional<Integer> page, Optional<Integer> pageSize) {
        return baseRepon(_giaoCaStaffService.findAllGiaoCaByStatus(basePageable(page, pageSize), TrangThaiGiaoCa.KET_THUC_CA));
    }

    @GetMapping("search")
    public ResponseEntity<?> searchGiaoCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("name") String name) {
        Page<QuanLyGiaoCaResponse> giaoCaResponsePage = _giaoCaStaffService.searchByName(basePageable(page, pageSize), name);
        return baseRepon(giaoCaResponsePage);
    }

    @GetMapping("rut-tien")
    public ResponseEntity<?> giaoCaCoTienRut(Optional<Integer> page, Optional<Integer> pageSize) {
        return baseRepon(_giaoCaStaffService.giaoCaCoTienRut(basePageable(page, pageSize)));
    }

    @GetMapping("by-time")
    public ResponseEntity<?> giaoCaByThoiGianNhanCa(Optional<Integer> page, Optional<Integer> pageSize, @RequestParam("time") String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Page<QuanLyGiaoCaResponse> giaoCaResponsePage = _giaoCaStaffService.giaoCaByThoiGianNhanCa(basePageable(page, pageSize), simpleDateFormat.parse(time));
        System.out.println(time);
        return baseRepon(giaoCaResponsePage);
    }
}
