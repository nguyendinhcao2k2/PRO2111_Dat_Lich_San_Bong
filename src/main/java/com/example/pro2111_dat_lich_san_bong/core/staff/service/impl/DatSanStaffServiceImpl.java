package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.FilterSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.HoaDonStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanBongStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.SanCaStaffResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.CaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.ThoiGianDatLichStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ViTienUserService;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailUtils;
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.*;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.example.pro2111_dat_lich_san_bong.model.response.MaillListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author caodinh
 */
@Service
@Transactional
public class DatSanStaffServiceImpl implements IDatSanStaffService {

    @Autowired
    private CaStaffRepository caStaffRepository;

    @Autowired
    private SanBongStaffRepository sanBongStaffRepository;

    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private HoaDonStaffRepository hoaDonStaffRepository;

    @Autowired
    private ThoiGianDatLichStaffRepository thoiGianDatLichStaffRepository;

    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;

    @Autowired
    private HoaDonSanCaUserRepository hoaDonSanCaUserRepository;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private SendMailWithBookings sendMailWithBookings;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private ViTienUserService viTienUserService;


    @Override
    public List<LoadSanBongRespose> loadSanBong() {
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, SanCaStaffResponse> checkSanCa = addDataToHashMap(localDateTime.toLocalDate());
        List<LoadSanBongRespose> loadSanBongResposeList = new ArrayList<>();
        List<CaStaffResponse> caStaffResponses = caStaffRepository.getCa();
        for (SanBongStaffResponse sanBongStaffResponse : sanBongStaffRepository.getAllSanBong()) {
            LoadSanBongRespose loadSanBongRespose = new LoadSanBongRespose();
            loadSanBongRespose.setIdSanBong(sanBongStaffResponse.getIdSanBong());
            loadSanBongRespose.setTenSanBong(sanBongStaffResponse.getTenSanBong());
            loadSanBongRespose.setGiaSan(sanBongStaffResponse.getGiaSan());
            loadSanBongRespose.setIdLoaiSan(sanBongStaffResponse.getIdLoaiSan());
            loadSanBongRespose.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
            List<LoadCaResponse> loadCaResponses = new ArrayList<>();
            for (CaStaffResponse caStaffResponse : caStaffResponses) {
                LoadCaResponse loadCaResponse = new LoadCaResponse();
                loadCaResponse.setIdCa(caStaffResponse.getIdCa());
                loadCaResponse.setTenCa(caStaffResponse.getTenCa());
                loadCaResponse.setThoiGianBatDau(caStaffResponse.getThoiGianBatDau().toString());
                loadCaResponse.setThoiGianKetthuc(caStaffResponse.getThoiGianKetThuc().toString());
                loadCaResponse.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
                loadCaResponse.setGia(caStaffResponse.getGiaCa() + sanBongStaffResponse.getGiaSan());
                loadCaResponse.setDate(localDateTime.toLocalDate().toString());
                loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                Object obj = checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse);
                if (obj != null) {
                    loadCaResponse.setIdHoaDonSanCa(hoaDonSanCaStaffRepository.findByIdSanCa((String) obj));
                }
                LocalDate localDateCompare = LocalDateTime.now().toLocalDate();
                if (localDateCompare.compareTo(localDateTime.toLocalDate()) == 0) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime localTimeCompare = LocalTime.parse(loadCaResponse.getThoiGianBatDau(), formatter);
                    if (localTime.isAfter(localTimeCompare)) {
                        loadCaResponse.setTrangThai(4);
                    }
                }
                loadCaResponses.add(loadCaResponse);
            }
            loadSanBongRespose.setLoadCaResponses(loadCaResponses);
            loadSanBongResposeList.add(loadSanBongRespose);
        }
        return loadSanBongResposeList;
    }

    @Override
    public List<LoadSanBongRespose> filterSanBong(FilterSanBongRequest filterSanBongRequest) {
        List<SanBongStaffResponse> sanBongStaffResponses = null;
        LocalDateTime localDateTime = LocalDateTime.now();
        String idSanBong = null;
        if (!filterSanBongRequest.getSanBong().equals("all")) {
            idSanBong = filterSanBongRequest.getSanBong();
            sanBongStaffResponses = sanBongStaffRepository.filterSanBong(idSanBong);
        } else {
            sanBongStaffResponses = sanBongStaffRepository.getAllSanBong();
        }
        if (!filterSanBongRequest.getDate().equals("none")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime = LocalDateTime.parse(filterSanBongRequest.getDate(), formatter);
        }
        LocalTime localTime = LocalTime.now();
        Map<String, SanCaStaffResponse> checkSanCa = addDataToHashMap(localDateTime.toLocalDate());
        List<LoadSanBongRespose> loadSanBongResposeList = new ArrayList<>();
        List<CaStaffResponse> caStaffResponses = caStaffRepository.getCa();
        for (SanBongStaffResponse sanBongStaffResponse : sanBongStaffResponses) {
            LoadSanBongRespose loadSanBongRespose = new LoadSanBongRespose();
            loadSanBongRespose.setIdSanBong(sanBongStaffResponse.getIdSanBong());
            loadSanBongRespose.setTenSanBong(sanBongStaffResponse.getTenSanBong());
            loadSanBongRespose.setGiaSan(sanBongStaffResponse.getGiaSan());
            loadSanBongRespose.setIdLoaiSan(sanBongStaffResponse.getIdLoaiSan());
            loadSanBongRespose.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
            List<LoadCaResponse> loadCaResponses = new ArrayList<>();
            for (CaStaffResponse caStaffResponse : caStaffResponses) {
                LoadCaResponse loadCaResponse = new LoadCaResponse();
                loadCaResponse.setIdCa(caStaffResponse.getIdCa());
                loadCaResponse.setTenCa(caStaffResponse.getTenCa());
                loadCaResponse.setThoiGianBatDau(caStaffResponse.getThoiGianBatDau().toString());
                loadCaResponse.setThoiGianKetthuc(caStaffResponse.getThoiGianKetThuc().toString());
                loadCaResponse.setLoaiSan(sanBongStaffResponse.getTenLoaiSan());
                loadCaResponse.setGia(caStaffResponse.getGiaCa() + sanBongStaffResponse.getGiaSan());
                loadCaResponse.setDate(localDateTime.toLocalDate().toString());
                loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                Object obj = checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse);
                if (obj != null) {
                    loadCaResponse.setIdHoaDonSanCa(hoaDonSanCaStaffRepository.findByIdSanCa((String) obj));
                }
                LocalDate localDateCompare = LocalDateTime.now().toLocalDate();
                if (localDateCompare.compareTo(localDateTime.toLocalDate()) == 0) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime localTimeCompare = LocalTime.parse(loadCaResponse.getThoiGianBatDau(), formatter);
                    if (localTime.isAfter(localTimeCompare)) {
                        loadCaResponse.setTrangThai(4);
                    }
                }
                loadCaResponses.add(loadCaResponse);
            }
            loadSanBongRespose.setLoadCaResponses(loadCaResponses);
            loadSanBongResposeList.add(loadSanBongRespose);
        }
        return loadSanBongResposeList;
    }

    @Override
    public boolean datLich(ThongTinNguoiDatRequest thongTinNguoiDatRequest, HttpServletRequest request) {
        checkDatLich(thongTinNguoiDatRequest.getThongTinLichDatRequests());
        String idHoaDon = createHoaDon(thongTinNguoiDatRequest);
        if (idHoaDon == null) {
            return false;
        }

        if (!createThoiGianDatLich(idHoaDon, thongTinNguoiDatRequest)) {
            return false;
        }
        List<SanCa> sanCas = createSanCa(thongTinNguoiDatRequest);
        if (sanCas == null) {
            return false;
        }
        if (!createHoaDonSanCa(sanCas, idHoaDon)) {
            return false;
        }
        sendMailDatLich(request, idHoaDon);
        return true;
    }

    @Override
    public void sendMailDatLich(HttpServletRequest request, String idhoaDon) {
        HoaDon hoaDon = hoaDonStaffRepository.findById(idhoaDon).get();
        //gửi mail
        try {
            List<HoaDonSendMailResponse> list = hoaDonSanCaUserRepository.getListHoaDonSanCa(hoaDon.getId());
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
    }

    @Override
    public List<HoaDonStaffResponse> getHoaDonByTrangThai() {
        return hoaDonStaffRepository.getHoaDonByTrangThai();
    }

    @Override
    public void huySanBong(String idHoaDon) {
        List<String> idSanCas = hoaDonSanCaStaffRepository.findIdSanCaByIdHoaDon(idHoaDon);
        if (idSanCas.size() > 0) {
            sanCaStaffRepository.deleteAllById(idSanCas);
        }
        hoaDonSanCaStaffRepository.deleteByIdHoaDon(idHoaDon);
        hoaDonStaffRepository.deleteById(idHoaDon);
    }

    @Override
    public List<HoaDonStaffResponse> filterHoaDon(FilterLichDatRequest filterLichDatRequest) {
        return hoaDonStaffRepository.filterHoaDon("%" + filterLichDatRequest.getTextString() + "%");
    }

    public void checkDatLich(List<ThongTinLichDatRequest> thongTinLichDatRequests) {
        Map<String, String> map = new HashMap<>();
        for (SanCaStaffResponse sanCaStaffResponse : sanCaStaffRepository.findAllSanCa()) {
            map.put(sanCaStaffResponse.getIdSanCa(), sanCaStaffResponse.getIdSanCa());
        }

        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinLichDatRequests) {
            String date = getTime(toLocalDate(thongTinLichDatRequest.getNgay()));
            String idSanCa = thongTinLichDatRequest.getId() + "+" + date;
            if (map.containsKey(idSanCa)) {
                throw new RestApiException("Một trong số sân bạn chọn đã có người đặt vui lòng chọn sân khác !");
            }
        }
    }

    public boolean createHoaDonSanCa(List<SanCa> sanCas, String idHoaDon) {
        List<HoaDonSanCa> hoaDonSanCas = new ArrayList<>();
        for (SanCa sanCa : sanCas) {
            HoaDonSanCa hoaDonSanCa = new HoaDonSanCa();
            hoaDonSanCa.setIdHoaDon(idHoaDon);
            hoaDonSanCa.setTienSan(sanCa.getGia());
            hoaDonSanCa.setMaQR(UUID.randomUUID().toString());
            hoaDonSanCa.setIdSanCa(sanCa.getId());
            hoaDonSanCa.setNgayDenSan(sanCa.getNgayDenSan());
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHO_NHAN_SAN.ordinal());
            hoaDonSanCas.add(hoaDonSanCa);
        }
        try {
            hoaDonSanCaStaffRepository.saveAll(hoaDonSanCas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(9);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 9; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    private String createHoaDon(ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
        SysParam param = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC);
        HoaDon hoaDon = new HoaDon();
        ViTienCoc viTienCoc = new ViTienCoc();
        double tongTien = 0;
        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinNguoiDatRequest.getThongTinLichDatRequests()) {
            tongTien += Double.parseDouble(thongTinLichDatRequest.getPrice());
            LichSuSanBong lichSuSanBong = new LichSuSanBong();
            lichSuSanBong.setNgayThucHien(LocalDateTime.now());
            lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal());
            lichSuSanBongAdminService.createOrUpdate(lichSuSanBong);
        }
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setEmail(thongTinNguoiDatRequest.getEmail());
        hoaDon.setSoDienThoaiNguoiDat(thongTinNguoiDatRequest.getSoDienThoai());
        hoaDon.setTenNguoiDat(thongTinNguoiDatRequest.getHoVaTen());
        hoaDon.setTrangThai(TrangThaiHoaDon.MOI_TAO.ordinal());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienCoc(tongTien * (Double.valueOf(param.getValue()) / 100));
        String maTienCoc = generateRandomString();
        hoaDon.setMaTienCoc(maTienCoc);


        try {
            HoaDon hoaDonA = hoaDonStaffRepository.save(hoaDon);
            viTienCoc.setTypePayment(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());
            viTienCoc.setSoTien(tongTien * (Double.valueOf(param.getValue()) / 100));
            viTienCoc.setIdHoaDon(hoaDonA.getId());
            viTienCoc.setNoiDung("Tien coc san bong");
            viTienCoc.setLoaiTien("VNd");
            viTienCoc.setTrangThai(TrangThaiViTien.BINH_THUONG.ordinal());
            viTienCoc.setThoiGianTao(Timestamp.valueOf(LocalDateTime.now()));
            viTienUserService.saveViTen(viTienCoc);


            return hoaDonA.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createThoiGianDatLich(String idHoaDon, ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
        List<ThoiGianDatLich> thoiGianDatLichList = new ArrayList<>();
        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinNguoiDatRequest.getThongTinLichDatRequests()) {
            String[] stringParts = thongTinLichDatRequest.getId().split("\\+");
            ThoiGianDatLich thoiGianDatLich = new ThoiGianDatLich();
            thoiGianDatLich.setNgayDat(LocalDateTime.now());
            thoiGianDatLich.setIdCa(stringParts[1]);
            thoiGianDatLich.setIdLoaiSan(stringParts[2]);
            thoiGianDatLich.setIdHoaDon(idHoaDon);
            thoiGianDatLichList.add(thoiGianDatLich);
        }
        try {
            thoiGianDatLichStaffRepository.saveAll(thoiGianDatLichList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SanCa> createSanCa(ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
        List<SanCa> sanCas = new ArrayList<>();
        List<LichSuSanBong> listLS = new ArrayList<>();
        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinNguoiDatRequest.getThongTinLichDatRequests()) {
            LocalDate localDate = toLocalDate(thongTinLichDatRequest.getNgay());
            String[] stringParts = thongTinLichDatRequest.getId().split("\\+");
            SanCa sanCa = new SanCa();
            sanCa.setIdCa(stringParts[1]);
            sanCa.setIdSanBong(stringParts[0]);
            sanCa.setThoiGianDat(LocalDateTime.now());
            sanCa.setNgayDenSan(localDate);
            sanCa.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal());
            sanCa.setGia(Double.parseDouble(thongTinLichDatRequest.getPrice()));
            sanCa.setId(sanCa.getIdSanBong() + "+" + sanCa.getIdCa() + "+" + stringParts[2] + "+" + getTime(localDate));
            sanCas.add(sanCa);

            LichSuSanBong lichSuSanBong = new LichSuSanBong();
            lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.DAT_LICH_HO.ordinal());
            lichSuSanBong.setNgayThucHien(LocalDateTime.now());
            listLS.add(lichSuSanBong);
        }
        try {
            lichSuSanBongAdminService.saveAll(listLS);
            return sanCaStaffRepository.saveAll(sanCas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTime(LocalDate localDate) {
        String day = "";
        String month = "";
        String year = "";
        if (localDate.getDayOfMonth() < 10) {
            day = "0" + localDate.getDayOfMonth();
        } else {
            day = String.valueOf(localDate.getDayOfMonth());
        }

        if (localDate.getMonthValue() < 10) {
            month = "0" + localDate.getMonthValue();
        } else {
            month = String.valueOf(localDate.getMonthValue());
        }

        year = String.valueOf(localDate.getYear());
        return day + month + year;
    }

    public Map<String, SanCaStaffResponse> addDataToHashMap(LocalDate localDate) {
        Map<String, SanCaStaffResponse> hashMap = new HashMap<>();
        List<SanCaStaffResponse> caStaffResponses = sanCaStaffRepository.findAllByDate(localDate);
        caStaffResponses.stream().forEach(cf -> hashMap.put(cf.getIdSanBong() + cf.getThoiGianBatDau(), cf));
        return hashMap;
    }

    public Object checkTimeSanCa(Map<String, SanCaStaffResponse> hashMap, String checkString, LoadCaResponse loadCaResponse) {
        if (hashMap.containsKey(checkString)) {
            SanCaStaffResponse sanCaStaffResponse = hashMap.get(checkString);
            loadCaResponse.setTrangThai(sanCaStaffResponse.getTrangThai());
            loadCaResponse.setIdResponse(sanCaStaffResponse.getIdSanCa());
            return sanCaStaffResponse.getIdSanCa();
        }
        return null;
    }

    public LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}

