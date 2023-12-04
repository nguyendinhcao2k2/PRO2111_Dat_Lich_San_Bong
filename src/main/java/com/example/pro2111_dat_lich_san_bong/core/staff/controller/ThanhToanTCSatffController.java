package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.schedule.runSchedule.RunJobHuyHDByOutTab;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoDonDatLichResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ViTienUserService;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiViTien;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/api/v1/staff/thanh-toan")
@SessionAttributes("HDCreateBill")
public class ThanhToanTCSatffController extends BaseController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HoaDonUserService hoaDonUserService;

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private ViTienUserService viTienUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private RunJobHuyHDByOutTab runJobHuyHDByOutTab;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;


    @PostMapping("/create-payment")
    public String submidOrder(@RequestParam(name = "HDrequest") String HDrequest,
                              HttpServletRequest request) throws JsonProcessingException {

        HoDonDatLichResponse HDCreateBill = new ObjectMapper().readValue(HDrequest, HoDonDatLichResponse.class);

        //thời gian hết thời gian giao dịch (tính bằng phút)
        int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        //tạo ra 1 luồng mới để tạo job kiểm trả tg hết tg thanh toán
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //thêm 1 phút sau khi tắt tab thanh toán thì hủy hóa đơn
                    Thread.sleep((miuteParam + 1) * 60 * 1000);
                    huyLichByThatBai(HDCreateBill.getIdHoaDon());
                    //runJobHuyHDByOutTab.khaiBaoInfoJobHuyHoaDon(HDCreateBill.getIdHoaDon(), miuteParam+1);
                } catch (Exception e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        //đường dẫn kết quả thanh toán
        baseUrl += "/api/v1/staff/thanh-toan/resul-payment/"+HDCreateBill.getIdHoaDon();
        String vnpayUrl = vnPayService.createOrder(HDCreateBill.getTienCoc().intValue(),
                HDCreateBill.getRemark(), baseUrl, miuteParam);

        return "redirect:" + vnpayUrl;

    }

    @GetMapping("/resul-payment/{id}")
    public String GetBillPayMent(@PathVariable("id") String idHoaDon, HttpServletRequest request, Model model) throws ParseException {
        int paymentStatus = vnPayService.orderReturn(request);


        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String currCode = request.getParameter("vnp_CurrCode");



        // Định dạng mong muốn
        String outputDateFormat = "yyyy-MM-dd hh:mm:ss";

        // Tạo đối tượng SimpleDateFormat cho định dạng ban đầu
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // Tạo đối tượng SimpleDateFormat cho định dạng mong muốn
        SimpleDateFormat outputDateFormatObj = new SimpleDateFormat(outputDateFormat);

        // Parse chuỗi vào đối tượng Date
        Date date = inputDateFormat.parse(paymentTime);

        // Format lại thành chuỗi theo định dạng mong muốn
        String thoiGianGD = outputDateFormatObj.format(date);

        Double soTienGD = Double.valueOf(totalPrice) / 100;

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        HoaDon hoaDon = (HoaDon) hoaDonUserService.findHoaDonById(idHoaDon);
        if (paymentStatus == 1) {
            hoaDon.setTrangThai(TrangThaiHoaDon.DA_COC.ordinal());
            hoaDonUserService.updateHoaDon(hoaDon);
            List<HoaDonSanCa> listHdSC = hoaDonSanCaUserService.findByIdHoaDon(hoaDon.getId());
            List<LichSuSanBong> listCreate = new ArrayList<>();
            for (HoaDonSanCa hoaDonSanCa : listHdSC) {
                LichSuSanBong lichSuSanBong = new LichSuSanBong();
                lichSuSanBong.setNgayThucHien(LocalDateTime.now());
                lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DAT_LICH_ONLINE.ordinal());
                listCreate.add(lichSuSanBong);
            }
            lichSuSanBongAdminService.saveAll(listCreate);
            //thanh toán thành công
            ViTienCoc viTienCoc = new ViTienCoc();
            viTienCoc.setIdHoaDon(hoaDon.getId());
            viTienCoc.setSoGiaoDich(transactionId);
            viTienCoc.setLoaiTien("VND");
            viTienCoc.setNoiDung(orderInfo);
            viTienCoc.setTrangThai(TrangThaiViTien.BINH_THUONG.ordinal());

            viTienCoc.setThoiGianTao(Timestamp.valueOf(thoiGianGD));
            viTienCoc.setSoTien(soTienGD);
            viTienCoc.setTypePayment(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());

            viTienUserService.saveViTen(viTienCoc);

            //gửi mail
            try {

                List<HoaDonSendMailResponse> list = hoaDonSanCaUserService.getLisTHDSC(hoaDon.getId());
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

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
                    context.setVariable("nguoiDat", hoaDon.getTenNguoiDat());
                    context.setVariable("sdt", hoaDon.getSoDienThoaiNguoiDat());

                    //thời gian đặt sân
                    context.setVariable("timeDat", formatter.format(hoaDon.getNgayTao()));
                    context.setVariable("tongTien", decimalFormat.format(hoaDon.getTongTien()));

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
                    sendMailWithBookings.sendEmailBookings(hoaDon.getEmail(), context, request);
                }

            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }

            return "staff/success-oder";
        } else {// thanh toán thất bại hoặc hết tg thanh toán
            huyLichByThatBai(hoaDon.getId());

            return "DemoVNPay/FailOder";
        }

    }

    private void huyLichByThatBai(String idHoaDon) {
        try {
            HoaDon hoaDon = hoaDonUserService.findHoaDonById(idHoaDon);

            //kiểm tr có đúng hóa đơn và trạng thái mới tạo không
            if (hoaDon != null && hoaDon.getTrangThai() != null
                    && hoaDon.getTrangThai() == TrangThaiHoaDon.MOI_TAO.ordinal()) {
                //lấy tất cả hóa đơn sân ca có idHoaDon
                List<HoaDonSanCa> list = hoaDonSanCaUserService.findAllByIdHoaDon(idHoaDon);
                for (HoaDonSanCa hoaDonSanCa : list
                ) {
                    String idSanCa = hoaDonSanCa.getIdSanCa();
                    sanCaUserService.deleteSanCaById(idSanCa);
                }
                hoaDonSanCaUserService.deleteAllByIdHoaDon(idHoaDon);
                hoaDonUserService.deleteHoaDonById(idHoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
