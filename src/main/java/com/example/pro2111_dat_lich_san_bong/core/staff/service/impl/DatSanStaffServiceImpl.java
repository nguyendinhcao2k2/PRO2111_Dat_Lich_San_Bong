package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinLichDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThongTinNguoiDatRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CaStaffResponse;
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
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.entity.ThoiGianDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<LoadSanBongRespose> loadSanBong(String date) {
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        if (!date.isBlank() || !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime = LocalDateTime.parse(date, formatter);
        }
        Map<String, SanCaStaffResponse> checkSanCa = addDataToHashMap(localDateTime);
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
                if (!checkTimeSanCa(checkSanCa, sanBongStaffResponse.getIdSanBong() + loadCaResponse.getThoiGianBatDau(), loadCaResponse)) {
                    loadCaResponse.setIdResponse(sanBongStaffResponse.getIdSanBong() + "+" + caStaffResponse.getIdCa() + "+" + sanBongStaffResponse.getIdLoaiSan());
                    LocalDate localDateCompare = LocalDateTime.now().toLocalDate();
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
        String idHoaDon = createHoaDon(thongTinNguoiDatRequest);
        if (idHoaDon == null) {
            return false;
        }

        if (!createThoiGianDatLich(idHoaDon, thongTinNguoiDatRequest)) {
            return false;
        }
        List<HoaDonSanCa> hoaDonSanCas = new ArrayList<>();
        List<SanCa> sanCas = createSanCa(thongTinNguoiDatRequest);
        if (sanCas == null) {
            return false;
        }
        for (SanCa sanCa : sanCas) {
            HoaDonSanCa hoaDonSanCa = new HoaDonSanCa();
            hoaDonSanCa.setIdSanCa(sanCa.getId());
        }

        if (!createHoaDonSanCa(hoaDonSanCas, idHoaDon)) {
            return false;
        }
        return true;
    }

    public boolean createHoaDonSanCa(List<HoaDonSanCa> hoaDonSanCas, String idHoaDon) {
        for (HoaDonSanCa hoaDonSanCa : hoaDonSanCas) {
            hoaDonSanCa.setIdHoaDon(idHoaDon);
            hoaDonSanCa.setTongTien(hoaDonSanCa.getTongTien());
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal());
        }
        try {
            hoaDonSanCaStaffRepository.saveAll(hoaDonSanCas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createHoaDon(ThongTinNguoiDatRequest thongTinNguoiDatRequest) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setSoDienThoaiNguoiDat(thongTinNguoiDatRequest.getSoDienThoai());
        hoaDon.setTenNguoiDat(thongTinNguoiDatRequest.getHoVaTen());
        hoaDon.setTrangThai(TrangThaiHoaDon.CHUA_THANH_TOAN.ordinal());
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
            String[] stringParts = thongTinLichDatRequest.getId().split("\\+");
            SanCa sanCa = new SanCa();
            sanCa.setIdCa(stringParts[1]);
            sanCa.setIdSanBong(stringParts[0]);
            sanCa.setThoiGianDat(LocalDateTime.now());
            sanCa.setTrangThai(TrangThaiSanCa.CHO_THANH_TOAN.ordinal());
            sanCa.setGia(Double.parseDouble(thongTinLichDatRequest.getPrice()));
            sanCa.setId(sanCa.getIdSanBong() + "+" + sanCa.getIdCa() + "+" + stringParts[2] + "+" + getTime());
            sanCas.add(sanCa);
        }
        try {
            return sanCaStaffRepository.saveAll(sanCas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTime() {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
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

    public Map<String, SanCaStaffResponse> addDataToHashMap(LocalDateTime localDateTime) {
        Map<String, SanCaStaffResponse> hashMap = new HashMap<>();
        LocalDateTime from = localDateTime.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999);
        List<SanCaStaffResponse> caStaffResponses = sanCaStaffRepository.findAllByDate(from, to);
        caStaffResponses.stream().forEach(cf -> hashMap.put(cf.getIdSanBong() + cf.getThoiGianBatDau(), cf));
        return hashMap;
    }

    public boolean checkTimeSanCa(Map<String, SanCaStaffResponse> hashMap, String checkString, LoadCaResponse loadCaResponse) {
        if (hashMap.containsKey(checkString)) {
            SanCaStaffResponse sanCaStaffResponse = hashMap.get(checkString);
            loadCaResponse.setTrangThai(sanCaStaffResponse.getTrangThai());
            loadCaResponse.setIdResponse(sanCaStaffResponse.getIdSanCa());
            return true;
        }
        return false;
    }
}
