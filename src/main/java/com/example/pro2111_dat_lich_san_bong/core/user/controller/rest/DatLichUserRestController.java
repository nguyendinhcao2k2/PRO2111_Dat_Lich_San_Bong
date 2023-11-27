package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DanhSachCaDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.PhieuDatLichUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoDonDatLichResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ThoiGianDLUserServiver;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiThoiGianDL;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/v1/user/dat-lich")
public class DatLichUserRestController extends BaseController {

    @Autowired
    private ThoiGianDLUserServiver thoiGianDLUserServiver;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private SanBongUserService sanBongUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @PostMapping("/form-dat-lich")
    public BaseResponse<Object> GetFormDatLich(@RequestBody PhieuDatLichUserRequest request) {

        try {


            Account account = session.getEntityAcount();

            Double tongTienSan = Double.valueOf(request.getTongTienSan());
            Double tongTienCoc = sysParamUserService.getTienCoc(tongTienSan);

            HoaDon hoaDon = new HoaDon();
            hoaDon.setEmail(request.getEmail());
            hoaDon.setIdAccount(account.getId());
            hoaDon.setSoDienThoaiNguoiDat(request.getSdt());
            hoaDon.setTenNguoiDat(request.getHoVaTen());
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTongTien(tongTienSan);
            hoaDon.setTienCoc(tongTienCoc);
            hoaDon.setTrangThai(TrangThaiHoaDon.MOI_TAO.ordinal());
            hoaDon.setGhiChu(request.getGhiChu());

            HoaDon hoaDonSave = hoaDonUserService.saveHoaDon(hoaDon);
            List listTGDL = new ArrayList<ThoiGianDatLich>();
            List listSanCa = new ArrayList<ThoiGianDatLich>();
            List listHoaDonSanCa = new ArrayList<ThoiGianDatLich>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            List<LichSuSanBong> lichSuSanBongs = new ArrayList<>();
            for (DanhSachCaDatRequest danhSachCaDatRequest : request.getDanhSachCaDatRequests()) {

                Double giaSanCa = Double.valueOf(danhSachCaDatRequest.getGiaSan());
                String idCa = danhSachCaDatRequest.getIdCa();
                //tạo thời gian đặt lịch
                ThoiGianDatLich thoiGianDatLich = new ThoiGianDatLich();
                thoiGianDatLich.setTrangThai(TrangThaiThoiGianDL.CHO_XAC_NHAN.ordinal());
                thoiGianDatLich.setNgayDat(LocalDateTime.now());
                thoiGianDatLich.setIdCa(idCa);
                thoiGianDatLich.setIdHoaDon(hoaDonSave.getId());
                thoiGianDatLich.setIdLoaiSan(request.getIdLoaiSan());
                thoiGianDatLich.setGiaDatLich(giaSanCa);

                LocalDate ngayDaLocalDate = LocalDate.parse(danhSachCaDatRequest.getNgayDa(), formatter);

                // Sau đó, nếu bạn cần sử dụng LocalDateTime, bạn có thể chuyển đổi LocalDate thành LocalDateTime:
                LocalDateTime ngayDaLocalDateTime = ngayDaLocalDate.atStartOfDay();

                thoiGianDatLich.setNgayDat(ngayDaLocalDateTime);

                listTGDL.add(thoiGianDatLich);

                int day = ngayDaLocalDateTime.getDayOfMonth();
                int month = ngayDaLocalDateTime.getMonthValue();
                int year = ngayDaLocalDateTime.getYear();
                String dateString = String.format("%02d%02d%04d", day, month, year);

                String textCheck = danhSachCaDatRequest.getIdCa() + "+" + request.getIdLoaiSan() + "+" + dateString;
                String idSanBong = getIdSanBongEmpty(textCheck, request.getIdLoaiSan());
                //tạo san ca
                SanCa sanCa = new SanCa();
                sanCa.setUserId(account.getId());
                sanCa.setId(idSanBong + "+" + textCheck);
                sanCa.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal());
                sanCa.setIdSanBong(idSanBong);
                sanCa.setGia(giaSanCa);
                sanCa.setNgayDenSan(ngayDaLocalDate);
                sanCa.setThoiGianDat(LocalDateTime.now());
                sanCa.setIdCa(idCa);
                listSanCa.add(sanCa);
                sanCaUserService.saveSanCa(sanCa);

                HoaDonSanCa hoaDonSanCa = new HoaDonSanCa();
                hoaDonSanCa.setIdHoaDon(hoaDonSave.getId());
                hoaDonSanCa.setIdSanCa(sanCa.getId());
                hoaDonSanCa.setNgayDenSan(ngayDaLocalDate);
                hoaDonSanCa.setMaQR(UUID.randomUUID().toString());
                hoaDonSanCa.setTienSan(giaSanCa);
                hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                listHoaDonSanCa.add(hoaDonSanCa);
            }


            thoiGianDLUserServiver.saveAllThoiGianDatLich(listTGDL);
//            sanCaUserService.saveAllSanCa(listSanCa);
            hoaDonSanCaUserService.saveAllHoaDonSanCa(listHoaDonSanCa);
            HoDonDatLichResponse response = new HoDonDatLichResponse();
            response.setRemark("Thanh toan tien coc dat lich san bong");
            response.setTienCoc(tongTienCoc);
            response.setIdHoaDon(hoaDon.getId());
            return new BaseResponse<>(HttpStatus.OK, response);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.GATEWAY_TIMEOUT, "Đặt lịch không thành công");
        }


    }

    private String getIdSanBongEmpty(String textCheck, String idLoaiSan) {
        String idSanBong = "";
        List<SanBong> list = sanBongUserService.getALlSanBongByIdLoaiSan(idLoaiSan);

        for (SanBong sanBong : list) {
            String idSanCa = sanBong.getId() + "+" + textCheck;
            SanCa sanCa = sanCaUserService.findSanCaById(idSanCa);

            if (sanCa == null || Objects.isNull(sanCa)) {
                idSanBong = sanBong.getId();
                break; // Exit the loop
            }
        }

        return idSanBong;
    }


}
