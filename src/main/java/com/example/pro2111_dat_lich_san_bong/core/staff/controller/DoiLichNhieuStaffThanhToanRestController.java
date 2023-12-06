package com.example.pro2111_dat_lich_san_bong.core.staff.controller;


import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.DoiLichNhieuRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.*;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuDoiLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/api/v1/staff/thanh-toan-nhieu")
public class DoiLichNhieuStaffThanhToanRestController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private LichSuViTienUserService lichSuViTienUserService;

    @Autowired
    private ViTienUserService viTienUserService;

    @Autowired
    private DoiLichUserService doiLichUserService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private HoaDonDoiLichUserService hoaDonDoiLichUserService;

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private LichSuDoiLichUserService lichSuDoiLichUserService;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    private List<DoiLichNhieuRequest> doiLichNhieuRequestList = new ArrayList<>();


    @PostMapping("find-list-doi-lich")
    public ResponseEntity<?> findAllListDoiLich(@RequestBody List<DoiLichNhieuRequest> list) {
        try {
            doiLichNhieuRequestList = list;
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error"));
        }

    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) throws ParseException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/staff/thanh-toan-nhieu/vnpay-payment";

        //thời gian hết thời gian giao dịch (tính bằng phút)
        int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());

        //Dinh dang ngay mong muon
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        // map ngay muon doi
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<HoaDonSanCa> listHoaDonSanCaUpdate = new ArrayList<>();
        List<SanCa> listSanCaUpdate = new ArrayList<>();
        List<HoaDonSanCa> listHoaDonSanCaCuThayDoiTrangThai = new ArrayList<>();
        List<SanCa> listSanCaCanCu = new ArrayList<>();
        for (DoiLichNhieuRequest items : doiLichNhieuRequestList) {

            //map ngay
            Date date = simpleDateFormat.parse(items.getNgayDoi().replace(" ", ""));
            //map ve kia ngay mong muon
            String dateMongMuon = dateFormat.format(date);

            String idCaAndLoaiSan = items.getIdCa() + "+" + items.getIdLoaiSan() + "+" + dateMongMuon;
            String idSanBongCheck = doiLichUserService.getIdSanBongEmpty(idCaAndLoaiSan, items.getIdLoaiSan());

            HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(items.getIdHDSC());
            SanCa sanCa = sanCaUserService.findSanCaById(hoaDonSanCa.getIdSanCa());
            HoaDon hoaDon = hoaDonUserService.findHoaDonById(hoaDonSanCa.getIdHoaDon());

            //update trang thai hoa don san ca cu
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());

            //id san ca
            String idSanCa = idSanBongCheck + "+" + idCaAndLoaiSan;

            //hoa don san ca
            HoaDonSanCa hoaDonSanCaUpdate = new HoaDonSanCa();
            LocalDate ngayDenSan = LocalDate.parse(items.getNgayDoi(), timeFormatter);
            hoaDonSanCaUpdate.setNgayDenSan(ngayDenSan);
            hoaDonSanCaUpdate.setTienSan(Double.valueOf(items.getTienSan()));
            hoaDonSanCaUpdate.setMaQR(hoaDonSanCa.getMaQR());
            hoaDonSanCaUpdate.setTrangThai(TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal());
            hoaDonSanCaUpdate.setIdLichSuViTien(hoaDonSanCa.getIdLichSuViTien());
            hoaDonSanCaUpdate.setIdGiaoCa(hoaDonSanCa.getIdGiaoCa());
            hoaDonSanCaUpdate.setIdHoaDon(hoaDonSanCa.getIdHoaDon());
            hoaDonSanCaUpdate.setCountLich(1);
            hoaDonSanCaUpdate.setIdSanCa(idSanCa);

            // san ca
            SanCa sanCaUpdate = new SanCa();
            sanCaUpdate.setNgayDenSan(ngayDenSan);
            sanCaUpdate.setGia(Double.valueOf(items.getTienSan()));
            sanCaUpdate.setIdCa(items.getIdCa());
            sanCaUpdate.setId(idSanCa);
            sanCaUpdate.setTrangThai(sanCa.getTrangThai());
            sanCaUpdate.setIdSanBong(idSanBongCheck);
            sanCaUpdate.setUserId(sanCa.getUserId());
            sanCaUpdate.setThoiGianDat(sanCa.getThoiGianDat());


            //create hoa don san ca, san ca
            listHoaDonSanCaUpdate.add(hoaDonSanCaUpdate);
            listSanCaUpdate.add(sanCaUpdate);


            //Đổi trang thái hóa đơn sân ca cũ
            listHoaDonSanCaCuThayDoiTrangThai.add(hoaDonSanCa);
            listSanCaCanCu.add(sanCa);
        }
        List<LichSuDoiLich> lichSuDoiLichList = new ArrayList<>();
        try {
            hoaDonSanCaUserService.saveAllHoaDonSanCa(listHoaDonSanCaUpdate);
            sanCaUserService.saveAllSanCa(listSanCaUpdate);
            hoaDonSanCaUserService.updateAll(listHoaDonSanCaCuThayDoiTrangThai);

            if (listSanCaCanCu.size() != listSanCaUpdate.size()) {
                System.out.println("Số lượng ko khớp!");
                return "Lỗi vui lòng thử lại!";
            }

            for (int i = 0; i < listHoaDonSanCaUpdate.size(); i++) {
                LichSuDoiLich lichSuDoiLich = new LichSuDoiLich();
                lichSuDoiLich.setIdSanCaCu(listSanCaCanCu.get(i).getId());
                lichSuDoiLich.setIdSanCaMoi(listSanCaUpdate.get(i).getId());
                lichSuDoiLich.setIdNguoiDung(commonSession.getUserId());
                lichSuDoiLich.setTrangThai(TrangThaiLichSuDoiLich.DOI_N_LICH.ordinal());
                lichSuDoiLichList.add(lichSuDoiLich);
            }
            lichSuDoiLichUserService.saveAll(lichSuDoiLichList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //tạo ra 1 luồng mới để tạo job kiểm trả tg hết tg thanh toán
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //thêm 1 phút sau khi tắt tab thanh toán thì hủy hóa đơn
                    Thread.sleep((miuteParam + 1) * 60 * 1000);

                    if (listHoaDonSanCaUpdate.size() == listSanCaUpdate.size()) {
                        for (int i = 0; i < listHoaDonSanCaUpdate.size(); i++) {
                            Boolean check = huyDoiLichThatBai(listHoaDonSanCaUpdate.get(i).getId());
                            if (check) {
                                //xóa hóa đơn sân ca mới
                                hoaDonSanCaUserService.deleteByIdHoaDonSanCa(listHoaDonSanCaUpdate.get(i).getId());
                                //xóa sân ca mới
                                sanCaUserService.deleteSanCaById(listSanCaUpdate.get(i).getId());
                                //update lại trạng thái sân cũ
                                List listUpdate = new ArrayList<>();
                                for (HoaDonSanCa hoaDonSanCa : listHoaDonSanCaCuThayDoiTrangThai) {
                                    hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                                    listUpdate.add(hoaDonSanCa);
                                }
                                hoaDonSanCaUserService.updateAll(listUpdate);

                                //xóa lịch sử đổi lịch
                                for (LichSuDoiLich lichSuDoiLich : lichSuDoiLichList) {
                                    lichSuDoiLichUserService.deleteById(lichSuDoiLich.getId());
                                }
                            }
                        }
                    }


                } catch (Exception e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl, miuteParam);
        return "redirect:" + vnpayUrl;
    }

    private Boolean huyDoiLichThatBai(String idHDSC) {
        HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findById(idHDSC);
        if (hoaDonSanCa != null && hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.CHO_DOI_SAN.ordinal()) {
            return true;
        }
        return false;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) throws ParseException {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String custom_param1 = request.getParameter("custom_param1");

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat originDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date dateTT = format.parse(paymentTime);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", currencyFormat.format(Integer.valueOf(totalPrice) / 100));
        model.addAttribute("paymentTime", originDate.format(dateTT));
        model.addAttribute("transactionId", transactionId);

        List<String> listSanCaMoi = new ArrayList<>();
        List<String> listSanCaCu = new ArrayList<>();
        List<HoaDonSanCa> listHoaDonSanCaUpdate = new ArrayList<>();
        List<HoaDonSanCa> listHoaDonSanCaCu = new ArrayList<>();
        Set<String> hoaDonListSanCaMoi = new HashSet<>();
        List<LichSuSanBong> listCreateLichSuSanBong = new ArrayList<>();
        List<LichSuDoiLich> lichSuDoiLichList = lichSuDoiLichUserService.findAllByIdNguoiDungAndTrangThaiList(commonSession.getUserId(), TrangThaiLichSuDoiLich.DOI_N_LICH.ordinal());
        Double phanTramTienCoc = Double.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC).getValue());
        if (paymentStatus == 1) {
            try {
                for (LichSuDoiLich lichSuDoiLich : lichSuDoiLichList) {
                    listSanCaMoi.add(lichSuDoiLich.getIdSanCaMoi());
                    listSanCaCu.add(lichSuDoiLich.getIdSanCaCu());
                    lichSuDoiLichUserService.deleteById(lichSuDoiLich.getId());
                }

                //cập nhật lại trạng thái sân mới
                for (String idSanCaMoi : listSanCaMoi) {
                    HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCaMoi);
                    hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());

                    //tim kiem hoa don
                    hoaDonListSanCaMoi.add(hoaDonSanCa.getIdHoaDon());

                    listHoaDonSanCaUpdate.add(hoaDonSanCa);

                    //create Lich su san bong
                    LichSuSanBong lichSuSanBong = new LichSuSanBong();
                    lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DOI_LICH.ordinal());
                    lichSuSanBong.setNgayThucHien(LocalDateTime.now());
                    listCreateLichSuSanBong.add(lichSuSanBong);
                }
                lichSuSanBongAdminService.saveAll(listCreateLichSuSanBong);
                hoaDonSanCaUserService.updateAll(listHoaDonSanCaUpdate);

                for (String idSanCaCu : listSanCaCu) {
                    HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCaCu);
                    listHoaDonSanCaCu.add(hoaDonSanCa);
                }


                //update lại hoa don
                Set<String> listHoaDonMail = new HashSet<>();
                for (String idHD : hoaDonListSanCaMoi) {
                    HoaDon hoaDon = hoaDonUserService.findHoaDonById(idHD);
                    for (int i = 0; i < listHoaDonSanCaUpdate.size(); i++) {
                        if (listHoaDonSanCaUpdate.size() == listHoaDonSanCaCu.size()) {
                            if (hoaDon.getId().equals(listHoaDonSanCaCu.get(i).getIdHoaDon())) {
                                hoaDon.setTongTien(hoaDon.getTongTien() - listHoaDonSanCaCu.get(i).getTienSan() + listHoaDonSanCaUpdate.get(i).getTienSan());
                                hoaDon.setTienCoc(hoaDon.getTongTien() * (phanTramTienCoc / 100));
                                hoaDonUserService.updateHoaDon(hoaDon);
                                listHoaDonMail.add(hoaDon.getId());
                                ViTienCoc viTienCoc = viTienUserService.findByIdHoaDon(hoaDon.getId());
                                viTienCoc.setSoTien(hoaDon.getTienCoc());
                                viTienUserService.updateViTien(viTienCoc);
                                Double tienDaoDong = listHoaDonSanCaUpdate.get(i).getTienSan() - listHoaDonSanCaCu.get(i).getTienSan();
                                if (tienDaoDong > 0) {
                                    LichSuViTien lichSuViTien = new LichSuViTien();
                                    lichSuViTien.setIdViTienCoc(viTienCoc.getId());
                                    lichSuViTien.setSoTien((tienDaoDong) * (phanTramTienCoc / 100));
                                    lichSuViTien.setLoaiTien("VND");
                                    lichSuViTien.setThoiGian(LocalDateTime.now());
                                    lichSuViTien.setTaiKhoanVi("TK" + Math.floor((Math.random() * 899999) + 100000));
                                    lichSuViTien.setLoaiBienDong(TrangThaiLoaiBienDong.CONG_TIEN);
                                    lichSuViTien.setTrangThai("0");
                                    lichSuViTien.setNguoiNhan("Công ty TNHH Đồng Đế");
                                    lichSuViTienUserService.saveOrUpdate(lichSuViTien);
                                }


                            }
                        }

                    }
                    //gửi mail
                    for (String items : listHoaDonMail) {
                        HoaDon hoaDonMail = hoaDonUserService.findHoaDonById(items);
                        List<HoaDonSendMailResponse> list = hoaDonSanCaUserService.getLisTHDSC(hoaDonMail.getId());
                        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        if (list.size() == 1) {
                            HoaDonSendMailResponse response = list.get(0);

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
                            context.setVariable("nguoiDat", hoaDonMail.getTenNguoiDat());
                            context.setVariable("sdt", hoaDonMail.getSoDienThoaiNguoiDat());

                            //thời gian đặt sân
                            context.setVariable("timeDat", hoaDonMail.getNgayTao());
                            context.setVariable("tongTien", decimalFormat.format(hoaDonMail.getTongTien()));

                            List<MaillListResponse> listResponses = new ArrayList<>();

                            for (HoaDonSendMailResponse response : list) {
                                MaillListResponse maillListResponse = new MaillListResponse();
                                maillListResponse.setIdHoaDonSanCa(response.getId());
                                maillListResponse.setGiaSan(decimalFormat.format(response.getTienSan()));
                                maillListResponse.setNgayDa(formatterNgayDa.format(response.getNgayDenSan()));
                                maillListResponse.setCa(response.getTenCa() + ": (" + response.getThoiGianBatDau() + "-" + response.getThoiGianKetThuc() + ")");
                                listResponses.add(maillListResponse);
                            }

                            context.setVariable("thoiGianList", listResponses);
                            sendMailWithBookings.sendEmailBookings(hoaDonMail.getEmail(), context, request);
                        }
                    }

                    //gửi mail

                }


                //xoa san cu
                for (String idSanCaCu : listSanCaCu) {
                    HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCaCu);
                    hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCa.getId());
                    sanCaUserService.deleteSanCaById(idSanCaCu);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "staff/success-oder";
        }
        try {
            //xóa sân mới đi

            for (HoaDonSanCa hoaDonSanCa : listHoaDonSanCaUpdate) {
                hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCa.getId());
            }

            for (String idSanCaMoi : listSanCaMoi) {
                sanCaUserService.deleteSanCaById(idSanCaMoi);
            }
            List<HoaDonSanCa> listUpdateHoaDonSanCaCu = new ArrayList<>();
            for (String idSanCaCu : listSanCaCu) {
                HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCaCu);
                hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
                listUpdateHoaDonSanCaCu.add(hoaDonSanCa);
            }
            hoaDonSanCaUserService.updateAll(listUpdateHoaDonSanCaCu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "staff/fail-order-staff";

    }
}
