package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

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
import com.example.pro2111_dat_lich_san_bong.core.utils.SendMailWithBookings;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Object obj = checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse);
                if (obj != null) {
                    loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                    LocalDate localDateCompare = LocalDateTime.now().toLocalDate();
                    loadCaResponse.setIdHoaDonSanCa(hoaDonSanCaStaffRepository.findByIdSanCa((String) obj));
                    if (localDateCompare.compareTo(localDateTime.toLocalDate()) == 0) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime localTimeCompare = LocalTime.parse(loadCaResponse.getThoiGianBatDau(), formatter);
                        if (localTime.isAfter(localTimeCompare)) {
                            loadCaResponse.setTrangThai(4);
                        }
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
                Object obj = checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse);
                if (obj != null) {
                    loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                    LocalDate localDateCompare = LocalDateTime.now().toLocalDate();
                    loadCaResponse.setIdHoaDonSanCa(hoaDonSanCaStaffRepository.findByIdSanCa((String) obj));
                    if (localDateCompare.compareTo(localDateTime.toLocalDate()) == 0) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime localTimeCompare = LocalTime.parse(loadCaResponse.getThoiGianBatDau(), formatter);
                        if (localTime.isAfter(localTimeCompare)) {
                            loadCaResponse.setTrangThai(4);
                        }
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
    public boolean datLich(ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
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
        return true;
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
            hoaDonSanCa.setIdSanCa(sanCa.getId());
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal());
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
        HoaDon hoaDon = new HoaDon();
        double tongTien = 0;
        for (ThongTinLichDatRequest thongTinLichDatRequest : thongTinNguoiDatRequest.getThongTinLichDatRequests()) {
            tongTien += Double.parseDouble(thongTinLichDatRequest.getPrice());
        }
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setEmail(thongTinNguoiDatRequest.getEmail());
        hoaDon.setSoDienThoaiNguoiDat(thongTinNguoiDatRequest.getSoDienThoai());
        hoaDon.setTenNguoiDat(thongTinNguoiDatRequest.getHoVaTen());
        hoaDon.setTrangThai(TrangThaiHoaDon.MOI_TAO.ordinal());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienCoc(tongTien * 0.2);
        String maTienCoc = generateRandomString();
        hoaDon.setMaTienCoc(maTienCoc);
        try {
            return hoaDonStaffRepository.save(hoaDon).getId();
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
        }
        try {
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

