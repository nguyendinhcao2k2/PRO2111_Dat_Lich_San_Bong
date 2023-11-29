package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichNhieuRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichDatResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.DoiLichNhieuUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("api/v1/user/doi-lich")
public class DoiLichNhieuUserRestController {
    @Autowired
    private CommonSession commonSession;

    @Autowired
    private DoiLichNhieuUserService doiLichNhieuUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private CaUserService caUserService;
    @Autowired
    private LoaiSanUserService loaiSanUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private DoiLichUserService doiLichUserService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private HoaDonDoiLichUserService hoaDonDoiLichUserService;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    @GetMapping("list-lich-doi")
    public ResponseEntity<?> getAllLichCoTheDoi() {
        try {
            SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_DOI_LICH);
            List<DoiLichNhieuUserResponse> responses = doiLichNhieuUserService.findListLichDoi(Integer.valueOf(param.getValue()), commonSession.getUserId());
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, responses));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid"));
        }
    }

    @GetMapping("/find-all-loai-san-ca")
    public ResponseEntity<?> findAllLoaiSanAndCa() {
        try {
            DoiLichDatResponse responses = new DoiLichDatResponse();
            responses.setLoaiSan(loaiSanUserService.findAll());
            responses.setCa(caUserService.findAll());
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, responses));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid"));
        }
    }

    @PostMapping("update-lich-nhieu")
    public ResponseEntity<?> updateLichNhieu(@RequestBody List<DoiLichNhieuRequest> list, HttpServletRequest requestMail) {
        //Dinh dang ngay mong muon
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        // map ngay muon doi
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<HoaDonSanCa> listHoaDonSanCaUpdate = new ArrayList<>();
        List<SanCa> listSanCaUpdate = new ArrayList<>();
        List<HoaDonSanCa> listHoaDonSanCaCanXoa = new ArrayList<>();
        List<SanCa> listSanCaCanXoa = new ArrayList<>();
        Set<String> listHoaDonGuiMail = new HashSet<>();
        List<LichSuSanBong> listCreateLichSuSanBong = new ArrayList<>();
        try {
            for (DoiLichNhieuRequest request : list) {

                //map ngay
                Date date = simpleDateFormat.parse(request.getNgayDoi().replace(" ", ""));
                //map ve kia ngay mong muon
                String dateMongMuon = dateFormat.format(date);

                String idCaAndLoaiSan = request.getIdCa() + "+" + request.getIdLoaiSan() + "+" + dateMongMuon;
                String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, request.getIdLoaiSan());

                HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(request.getIdHDSC());
                SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());
                HoaDon hoaDon = hoaDonUserService.findHoaDonById(hoaDonSanCa.getIdHoaDon());
                listHoaDonGuiMail.add(hoaDon.getId());

                //id san ca
                String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;

                //hoa don san ca
                HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
                LocalDate ngayDenSan = LocalDate.parse(request.getNgayDoi(), timeFormatter);
                hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
                hoaDonSanCaUpdate.setTienSan(Double.valueOf(request.getTienSan()));
                hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
                hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
                hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
                hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
                hoaDonSanCaUpdate.setCountLich(1);
                hoaDonSanCaUpdate.setIdSanCa(idSanCa);

                // san ca
                SanCa sanCaUpdate = new SanCa();
                sanCaUpdate.setNgayDenSan(ngayDenSan);
                sanCaUpdate.setGia(Double.valueOf(request.getTienSan()));
                sanCaUpdate.setIdCa(request.getIdCa());
                sanCaUpdate.setId(idSanCa);
                sanCaUpdate.setTrangThai(sanCa.getTrangThai());
                sanCaUpdate.setIdSanBong(idSanBongCheck);
                sanCaUpdate.setUserId(sanCa.getUserId());
                sanCaUpdate.setThoiGianDat(sanCa.getThoiGianDat());

                //update lai tong tien hoa don
                hoaDon.setTongTien(hoaDon.getTongTien() - hoaDonSanCa.getTienSan() + Double.valueOf(request.getTienSan()));
                hoaDonDoiLichUserService.update(hoaDon);

                //create hoa don san ca, san ca
                listHoaDonSanCaUpdate.add(hoaDonSanCaUpdate);
                listSanCaUpdate.add(sanCaUpdate);


                //xoa san cu di
                listHoaDonSanCaCanXoa.add(hoaDonSanCa);
                listSanCaCanXoa.add(sanCa);

                //create Lich su san bong
                LichSuSanBong lichSuSanBong = new LichSuSanBong();
                lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DOI_LICH.ordinal());
                lichSuSanBong.setNgayThucHien(LocalDateTime.now());
                listCreateLichSuSanBong.add(lichSuSanBong);
            }
            lichSuSanBongAdminService.saveAll(listCreateLichSuSanBong);
            //save hoa don san ca new
            listHoaDonSanCaUpdate.get(0).setTienCocThua(list.get(0).getTienCocThua());
            hoaDonSanCaUserService.saveAllHoaDonSanCa(listHoaDonSanCaUpdate);
            sanCaUserService.saveAllSanCa(listSanCaUpdate);

            //delete san cu
            for (HoaDonSanCa item : listHoaDonSanCaCanXoa) {
                hoaDonSanCaUserService.deleteByIdHoaDonSanCa(item.getId());
            }
            for (SanCa item : listSanCaCanXoa) {
                sanCaUserService.deleteSanCaById(item.getId());
            }
            //gửi mail
            for (String items : listHoaDonGuiMail) {
                HoaDon hoaDon = hoaDonUserService.findHoaDonById(items);
                List<HoaDonSendMailResponse> listMail = hoaDonSanCaUserService.getLisTHDSC(hoaDon.getId());
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (listMail.size() == 1) {
                    HoaDonSendMailResponse response = listMail.get(0);

                    SendMailRequest sendMailRequest = new SendMailRequest();
                    sendMailRequest.setTitle("Phiếu Đặt Lịch");
                    sendMailRequest.setNguoiDat(response.getDisplayName());
                    sendMailRequest.setNguoiNhanMail(response.getEmail());
                    sendMailRequest.setQrCodeData(response.getMaQR());
                    sendMailRequest.setNgayDat(formatter.format(response.getNgayTao()));
                    sendMailRequest.setTimeStart(response.getThoiGianBatDau());
                    sendMailRequest.setTimeEnd(response.getThoiGianKetThuc());
                    sendMailRequest.setGiaTien(response.getTienSan());
                    sendMailRequest.setNgayCheckIn(formatterNgayDa.format(response.getNgayDenSan()));

                    sendMailUtils.sendEmail(sendMailRequest);
                } else if (list.size() > 1) {

                    Context context = new Context();
                    context.setVariable("nguoiDat", hoaDon.getTenNguoiDat());
                    context.setVariable("sdt", hoaDon.getSoDienThoaiNguoiDat());

                    //thời gian đặt sân
                    context.setVariable("timeDat", hoaDon.getNgayTao());
                    context.setVariable("tongTien", decimalFormat.format(hoaDon.getTongTien()));

                    List<MaillListResponse> listResponses = new ArrayList<>();

                    for (HoaDonSendMailResponse response : listMail) {
                        MaillListResponse maillListResponse = new MaillListResponse();
                        maillListResponse.setIdHoaDonSanCa(response.getId());
                        maillListResponse.setGiaSan(decimalFormat.format(response.getTienSan()));
                        maillListResponse.setNgayDa(formatterNgayDa.format(response.getNgayDenSan()));
                        maillListResponse.setCa(response.getTenCa() + ": (" + response.getThoiGianBatDau() + "-" + response.getThoiGianKetThuc() + ")");
                        listResponses.add(maillListResponse);
                    }

                    context.setVariable("thoiGianList", listResponses);
                    sendMailWithBookings.sendEmailBookings(hoaDon.getEmail(), context, requestMail);
                }
            }
            //gửi mail
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }

    }
}
