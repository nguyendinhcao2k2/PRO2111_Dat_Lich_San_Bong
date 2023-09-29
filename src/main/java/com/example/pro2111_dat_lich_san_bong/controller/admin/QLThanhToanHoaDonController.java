package com.example.pro2111_dat_lich_san_bong.controller.admin;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.DichVu;
import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.entity.ViTien;
import com.example.pro2111_dat_lich_san_bong.model.request.HoaDonPhieuDatLichRequest;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import com.example.pro2111_dat_lich_san_bong.service.AccountService;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import com.example.pro2111_dat_lich_san_bong.service.PhieuDatLichService;
import com.example.pro2111_dat_lich_san_bong.service.ViTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@Controller
@RequestMapping("api/v1/admin/quan-ly-hoa-don-thanh-toan")
public class QLThanhToanHoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private PhieuDatLichService phieuDatLichService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SanCaRepository sanCaRepository;
    @Autowired
    private SanBongRepository sanBongRepository;
    @Autowired
    private CaRepository caRepository;
    @Autowired
    private ViTienService viTienService;
    private List<PhieuDatLich> listPhieuDatLichs = new ArrayList<>();
    List<HoaDonPhieuDatLichRequest> listHoaDonPhieuDatLichRequest = new ArrayList<>();

    @GetMapping("/all")
    public String viewThanhToanHoaDon(Model model) {
        List<HoaDonPhieuDatLichRequest> listHoaDonPhieuDatLichRequest = showHoaDonPhieuDatLich(model);
        model.addAttribute("listHoaDonPhieuDatLichRequest", listHoaDonPhieuDatLichRequest);
        return "user/ql-hoa-don";
    }

    private List<HoaDonPhieuDatLichRequest> showHoaDonPhieuDatLich(Model model) {
        listHoaDonPhieuDatLichRequest.clear();
        listPhieuDatLichs = phieuDatLichService.getPhieuDatLichs();
        for (PhieuDatLich phieuDatLich : listPhieuDatLichs) {
            SanCa sanCa = sanCaRepository.findById(phieuDatLich.getIdSanCa()).get();
            Account account = accountService.getOneAccount(phieuDatLich.getIdAccount());
            SanBong sanBong = sanBongRepository.findById(sanCa.getIdSanBong()).get();
            Ca ca = caRepository.findById(sanCa.getIdCa()).get();
            listHoaDonPhieuDatLichRequest.add(new HoaDonPhieuDatLichRequest(phieuDatLich.getId(), account.getDisplayName(), account.getSoDienThoai(), sanBong.getTenSanBong(), ca.getTenCa(), phieuDatLich.getGioBatDau(), phieuDatLich.getGioKetThuc(), phieuDatLich.getTongTienSanCa(), phieuDatLich.getThoiGianCheckIn(), null, phieuDatLich.getTrangThai()));
        }
        return listHoaDonPhieuDatLichRequest;
    }

    @GetMapping("/show-phieu-dat-lich/{id}")
    public String showPhieuDatLichHoaDon(Model model, @PathVariable(name = "id") String id) {
        List<HoaDonPhieuDatLichRequest> listHoaDonPhieuDatLichRequest = showHoaDonPhieuDatLich(model);
        model.addAttribute("listHoaDonPhieuDatLichRequest", listHoaDonPhieuDatLichRequest);
        PhieuDatLich phieuDatLich = phieuDatLichService.getOnePhieuDatLich(id);
        Account account = accountService.getOneAccount(phieuDatLich.getIdAccount());
        ViTien viTien = viTienService.getOneViTien(account.getIdViTien());
        System.out.println(account.getDisplayName());
        model.addAttribute("account", account);
        model.addAttribute("viTien", viTien);
        return "user/ql-hoa-don";
    }
}
