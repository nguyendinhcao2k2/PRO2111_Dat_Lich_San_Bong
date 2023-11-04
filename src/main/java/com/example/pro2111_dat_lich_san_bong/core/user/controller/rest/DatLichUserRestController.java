package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DanhSachCaDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.PhieuDatLichUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ThoiGianDLUserServiver;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiThoiGianDL;
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
import java.util.List;

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

    @PostMapping("/form-dat-lich")
    public BaseResponse<String> GetFormDatLich(@RequestBody PhieuDatLichUserRequest request){

        try {
            Account account = session.getEntityAcount();

            HoaDon hoaDon = new HoaDon();
            hoaDon.setEmail(request.getEmail());
            hoaDon.setIdAccount(account.getId());
            hoaDon.setSoDienThoaiNguoiDat(request.getSdt());
            hoaDon.setTenNguoiDat(account.getDisplayName());
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTrangThai(TrangThaiHoaDon.MOI_TAO.ordinal());

            HoaDon hoaDonSave= hoaDonUserService.saveHoaDon(hoaDon);
            List list = new ArrayList< ThoiGianDatLich >();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (DanhSachCaDatRequest danhSachCaDatRequest : request.getDanhSachCaDatRequests()) {
                ThoiGianDatLich thoiGianDatLich = new ThoiGianDatLich();
                thoiGianDatLich.setTrangThai(TrangThaiThoiGianDL.MOI_TAO.ordinal());
                thoiGianDatLich.setNgayDat(LocalDateTime.now());
                thoiGianDatLich.setIdCa(danhSachCaDatRequest.getIdCa());
                thoiGianDatLich.setIdHoaDon(hoaDonSave.getId());
                thoiGianDatLich.setIdLoaiSan(request.getIdLoaiSan());

                LocalDate ngayDaLocalDate = LocalDate.parse(danhSachCaDatRequest.getNgayDa(), formatter);

                // Sau đó, nếu bạn cần sử dụng LocalDateTime, bạn có thể chuyển đổi LocalDate thành LocalDateTime:
                LocalDateTime ngayDaLocalDateTime = ngayDaLocalDate.atStartOfDay();

                thoiGianDatLich.setNgayDat(ngayDaLocalDateTime);

                list.add(thoiGianDatLich);
            }
            thoiGianDLUserServiver.saveAllThoiGianDatLich(list);
        }catch (Exception e){
            e.printStackTrace();
            return  new BaseResponse<>(HttpStatus.OK,"Đặt lịch không thành công");
        }

        return  new BaseResponse<>(HttpStatus.OK,"Đặt lịch thành công");
    }
}
