package com.example.generate_DB;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.LuatSanRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.LuatSanAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LuatSanAdminService;
import com.example.pro2111_dat_lich_san_bong.entity.*;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author thepv
 */

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = "com.example.pro2111_dat_lich_san_bong.repository"
)
public class DBGenerator implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CaRepository caRepository;
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Autowired
    private DichVuRepository dichVuRepository;
    @Autowired
    private DoThueRepository doThueRepository;
    @Autowired
    private HangKhachHangRepository hangKhachHangRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;


    @Autowired
    private LichSuChatRepository lichSuChatRepository;
    @Autowired
    private LoaiSanRepository loaiSanRepository;

    @Autowired
    private NuocUongRepository nuocUongRepository;
    @Autowired
    private PhuPhiHoaDonRepository phuPhiHoaDonRepository;
    @Autowired
    private PhuPhiRepository phuPhiRepository;
    @Autowired
    private SanBongRepository sanBongRepository;

    @Autowired
    private SanCaRepository sanCaRepository;
    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Autowired
    private ViTienRepository viTienRepository;

    @Autowired
    private SYSParamRepository sysParamRepository;

    @Autowired
    private LuatSanRepository luatSanRepository;

    private final boolean IS_RELEASE = false;

    @Override
    public void run(String... args) throws Exception {

        //start bảng sys_param cấu hình phần mền
        SysParam param1 = new SysParam();
        param1.setCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC);
        param1.setTrangThai(0); //đang hoạt động
        param1.setName("phần trăm số tiền cọc");
        param1.setValue("50");
        param1.setNote("giá trị (cột value) để tính phần trăm của tiền cần cọc bằng cách: tiền coc = tiền sân ca*(value/100)");
        param1.setType("%"); //đơn vị của value
        param1.setId(sysParamRepository.save(param1).getId());


        SysParam param2 = new SysParam();
        param2.setCode(SYSParamCodeConstant.THOI_GIAN_HET_GD);
        param2.setTrangThai(0); //đang hoạt động
        param2.setName("Thời gian kết thúc giao dịch");
        param2.setValue("30");
        param2.setNote("giá trị (cột value) để tính thời gian thanh toán bằng vnpay hết thời gian này giao dịch thành thất bại");
        param2.setType("phút"); //đơn vị của value
        param2.setId(sysParamRepository.save(param2).getId());

        SysParam param3 = new SysParam();
        param3.setCode(SYSParamCodeConstant.THOI_GIAN_THONG_BAO);
        param3.setTrangThai(0); //đang hoạt động
        param3.setName("Thời gian gửi thông báo trước ca");
        param3.setValue("30");
        param3.setNote("giá trị (cột value) để tính thời gian gửi thông báo qua mail trước thời gian bắt đầu ca");
        param3.setType("phút"); //đơn vị của value
        param3.setId(sysParamRepository.save(param3).getId());

        SysParam param4 = new SysParam();
        param4.setCode(SYSParamCodeConstant.THOI_GIAN_DOI_LICH);
        param4.setTrangThai(0); //đang hoạt động
        param4.setName("Thời gian cho phép đổi lịch");
        param4.setValue("1");
        param4.setNote("giá trị (cột value) để tính thời gian cho phép người dùng đổi lích trước ngày đá");
        param4.setType("ngày"); //đơn vị của value
        param4.setId(sysParamRepository.save(param4).getId());

        SysParam param5 = new SysParam();
        param5.setCode(SYSParamCodeConstant.THOI_GIAN_CHO_PHEP_DAT_LICH);
        param5.setTrangThai(0); //đang hoạt động
        param5.setName("Thời gian cho phép đặt lịch");
        param5.setValue("30");
        param5.setNote("giá trị (cột value) để tính thời gian cho phép người dùng đặt lịch trước thời gian ca");
        param5.setType("phút"); //đơn vị của value
        param5.setId(sysParamRepository.save(param5).getId());

        SysParam param6 = new SysParam();
        param6.setCode(SYSParamCodeConstant.THOI_GIAN_DUOC_PHEP_CHECK_IN);
        param6.setTrangThai(0);
        param6.setName("Thời gian cho phép check-in");
        param6.setValue("30");
        param6.setNote("giá trị (cột value) để tính thời gian cho phép người dùng check in");
        param6.setType("Phút");
        param6.setId(sysParamRepository.save(param6).getId());

        //end bảng sys_param cấu hình phần mền

        //start chuc vu
        ChucVu adminRole = new ChucVu();
        adminRole.setTrangThai(0); //o:hoat dong
        adminRole.setTenChucVu("ROLE_ADMIN");
        adminRole.setId(chucVuRepository.save(adminRole).getId());

        ChucVu khachHangRole = new ChucVu();
        khachHangRole.setTrangThai(0); //o:hoat dong
        khachHangRole.setTenChucVu("ROLE_USER");
        khachHangRole.setId(chucVuRepository.save(khachHangRole).getId());

        ChucVu nhanVienRole = new ChucVu();
        nhanVienRole.setTrangThai(0); //o:hoat dong
        nhanVienRole.setTenChucVu("ROLE_STAFF");
        nhanVienRole.setId(chucVuRepository.save(nhanVienRole).getId());
        //end chuc vu

        //start vi tien
       /* ViTienCoc viTienCocKH1 = new ViTienCoc();
        viTienCocKH1.setSoGiaoDich("004444444444");
        viTienCocKH1.setLoaiTien("VND");
        viTienCocKH1.setTrangThai(0);
        viTienCocKH1.setThoiGianTao(Timestamp.valueOf("2023-09-17 10:09:00"));
        viTienCocKH1.setSoTien(200000.0);
        viTienCocKH1.setId(viTienRepository.save(viTienCocKH1).getId());

        ViTienCoc viTienCocKH2 = new ViTienCoc();
        viTienCocKH2.setSoGiaoDich("007777777777");
        viTienCocKH2.setLoaiTien("VND");
        viTienCocKH2.setTrangThai(0);
        viTienCocKH2.setThoiGianTao(Timestamp.valueOf("2023-09-17 10:09:00"));
        viTienCocKH2.setSoTien(200000.0);
        viTienCocKH2.setId(viTienRepository.save(viTienCocKH2).getId());*/


        //end vi tien

        //start account

        Account kh1 = new Account();
        kh1.setEmail("taikhoan1@gamil.com");
        kh1.setSoDienThoai("0312345678");
        kh1.setDisplayName("tai khoản 1");
        kh1.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        kh1.setTrangThai(0); //trang thái 0: hoạt động
        kh1.setIdChucVu(khachHangRole.getId());
        kh1.setTaiKhoan("account1");
        kh1.setId(accountRepository.save(kh1).getId());


        Account kh2 = new Account();
        kh2.setEmail("taikhoan2@gamil.com");
        kh2.setSoDienThoai("0312345678");
        kh2.setDisplayName("tai khoản 2");
        kh2.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        kh2.setTrangThai(0); //trang thái 0: hoạt động
        kh2.setIdChucVu(khachHangRole.getId());
        kh2.setTaiKhoan("account2");
        kh2.setId(accountRepository.save(kh2).getId());

        Account nhanVien1 = new Account();
        nhanVien1.setEmail("nhanVien1@gamil.com");
        nhanVien1.setSoDienThoai("0312345678");
        nhanVien1.setDisplayName("nhan vien 1");
        nhanVien1.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        nhanVien1.setTrangThai(0); //trang thái 0: hoạt động
        nhanVien1.setIdChucVu(nhanVienRole.getId());
        nhanVien1.setTaiKhoan("nhanVien1");
        nhanVien1.setId(accountRepository.save(nhanVien1).getId());

        Account nhanVien2 = new Account();
        nhanVien2.setEmail("nhanVien2@gamil.com");
        nhanVien2.setSoDienThoai("0312345678");
        nhanVien2.setDisplayName("nhan vien 2");
        nhanVien2.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        nhanVien2.setTrangThai(0); //trang thái 0: hoạt động
        nhanVien2.setIdChucVu(nhanVienRole.getId());
        nhanVien2.setTaiKhoan("nhanVien2");
        nhanVien2.setId(accountRepository.save(nhanVien2).getId());

        Account admin1 = new Account();
        admin1.setEmail("sydvph19885@fpt.edu.vn");
        admin1.setSoDienThoai("0369569225");
        admin1.setDisplayName("admin 1");
        admin1.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        admin1.setTrangThai(0); //trang thái 0: hoạt động
        admin1.setIdChucVu(adminRole.getId());
        admin1.setTaiKhoan("admin1");
        admin1.setId(accountRepository.save(admin1).getId());

        //end account
        //start ca
        Ca ca1 = new Ca();
        ca1.setTenCa("Ca 1");
        ca1.setGiaCa(200000.0);
        ca1.setThoiGianBatDau(Time.valueOf("06:00:00"));
        ca1.setThoiGianKetThuc(Time.valueOf("08:30:00"));
        ca1.setTrangThai(0);
        ca1.setId(caRepository.save(ca1).getId());

        Ca ca2 = new Ca();
        ca2.setTenCa("Ca 2");
        ca2.setGiaCa(200000.0);
        ca2.setThoiGianBatDau(Time.valueOf("9:00:00"));
        ca2.setThoiGianKetThuc(Time.valueOf("11:30:00"));
        ca2.setTrangThai(0);
        ca2.setId(caRepository.save(ca2).getId());

        Ca ca3 = new Ca();
        ca3.setTenCa("Ca 3");
        ca3.setGiaCa(200000.0);
        ca3.setThoiGianBatDau(Time.valueOf("12:00:00"));
        ca3.setThoiGianKetThuc(Time.valueOf("14:30:00"));
        ca3.setTrangThai(0);
        ca3.setId(caRepository.save(ca3).getId());

        Ca ca4 = new Ca();
        ca4.setTenCa("Ca 4");
        ca4.setGiaCa(150000.0);
        ca4.setThoiGianBatDau(Time.valueOf("15:00:00"));
        ca4.setThoiGianKetThuc(Time.valueOf("17:30:00"));
        ca4.setTrangThai(0);
        ca4.setId(caRepository.save(ca4).getId());

        Ca ca5 = new Ca();
        ca5.setTenCa("Ca 5");
        ca5.setGiaCa(150000.0);
        ca5.setThoiGianBatDau(Time.valueOf("18:00:00"));
        ca5.setThoiGianKetThuc(Time.valueOf("20:30:00"));
        ca5.setTrangThai(0);
        ca5.setId(caRepository.save(ca5).getId());

        Ca ca6 = new Ca();
        ca6.setTenCa("Ca 6");
        ca6.setGiaCa(150000.0);
        ca6.setThoiGianBatDau(Time.valueOf("21:00:00"));
        ca6.setThoiGianKetThuc(Time.valueOf("23:30:00"));
        ca6.setTrangThai(0);
        ca6.setId(caRepository.save(ca6).getId());


        //end ca
        //Loai san start

        LoaiSan loaiSan1 = new LoaiSan();
        loaiSan1.setTenLoaiSan("Sân 5");
        loaiSan1.setGiaSan(250000.0);
        loaiSan1.setMoTa("Sân giành cho mỗi đội 5 người ");
        loaiSan1.setTrangThai(0);
        loaiSan1.setId(loaiSanRepository.save(loaiSan1).getId());

        LoaiSan loaiSan2 = new LoaiSan();
        loaiSan2.setTenLoaiSan("Sân 7");
        loaiSan2.setGiaSan(300000.0);
        loaiSan2.setMoTa("Sân giành cho mỗi đội 7 người ");
        loaiSan2.setTrangThai(0);
        loaiSan2.setId(loaiSanRepository.save(loaiSan2).getId());

        LoaiSan loaiSan3 = new LoaiSan();
        loaiSan3.setTenLoaiSan("Sân 11");
        loaiSan3.setGiaSan(350000.0);
        loaiSan3.setMoTa("Sân giành cho mỗi đội 11 người ");
        loaiSan3.setTrangThai(0);
        loaiSan3.setId(loaiSanRepository.save(loaiSan3).getId());

        //Loai san end

        //San bong start
        SanBong sanBong1 = new SanBong();
        sanBong1.setTenSanBong("Sân 1");
        sanBong1.setGiaSan(loaiSan1.getGiaSan());
        sanBong1.setIdLoaiSan(loaiSan1.getId());
        sanBong1.setTrangThai(0);
        sanBong1.setId(sanBongRepository.save(sanBong1).getId());

        SanBong sanBong2 = new SanBong();
        sanBong2.setTenSanBong("Sân 2");
        sanBong2.setGiaSan(loaiSan2.getGiaSan());
        sanBong2.setIdLoaiSan(loaiSan2.getId());
        sanBong2.setTrangThai(0);
        sanBong2.setId(sanBongRepository.save(sanBong2).getId());

        SanBong sanBong3 = new SanBong();
        sanBong3.setTenSanBong("Sân 3");
        sanBong3.setGiaSan(loaiSan2.getGiaSan());
        sanBong3.setIdLoaiSan(loaiSan2.getId());
        sanBong3.setTrangThai(0);
        sanBong3.setId(sanBongRepository.save(sanBong3).getId());

        SanBong sanBong4 = new SanBong();
        sanBong4.setTenSanBong("Sân 4");
        sanBong4.setGiaSan(loaiSan2.getGiaSan());
        sanBong4.setIdLoaiSan(loaiSan2.getId());
        sanBong4.setTrangThai(0);
        sanBong4.setId(sanBongRepository.save(sanBong4).getId());

        SanBong sanBong5 = new SanBong();
        sanBong5.setTenSanBong("Sân 5");
        sanBong5.setGiaSan(loaiSan2.getGiaSan());
        sanBong5.setIdLoaiSan(loaiSan2.getId());
        sanBong5.setTrangThai(0);
        sanBong5.setId(sanBongRepository.save(sanBong5).getId());

        SanBong sanBong6 = new SanBong();
        sanBong6.setTenSanBong("Sân 6");
        sanBong6.setGiaSan(loaiSan2.getGiaSan());
        sanBong6.setIdLoaiSan(loaiSan2.getId());
        sanBong6.setTrangThai(0);
        sanBong6.setId(sanBongRepository.save(sanBong6).getId());

        SanBong sanBong7 = new SanBong();
        sanBong7.setTenSanBong("Sân 7");
        sanBong7.setGiaSan(loaiSan3.getGiaSan());
        sanBong7.setIdLoaiSan(loaiSan3.getId());
        sanBong7.setTrangThai(0);
        sanBong7.setId(sanBongRepository.save(sanBong7).getId());

        //end san bong
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMuuuu");
        String dateId = localDateTime.format(formatter);

       /* SanCa san1Ca1 = new SanCa();
        san1Ca1.setIdSanBong(sanBong1.getId());
        san1Ca1.setIdCa(ca1.getId());
        san1Ca1.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal()); //0 chờ nhận sân
        san1Ca1.setThoiGianDat(localDateTime);
        san1Ca1.setNgayDenSan(LocalDate.now());
        san1Ca1.setGia(sanBong1.getGiaSan() + ca1.getGiaCa());
        san1Ca1.setId(getIdSanCa(sanBong1, ca1, dateId));
        san1Ca1.setUserId(kh1.getId());
        sanCaRepository.save(san1Ca1);

        SanCa san1Ca2 = new SanCa();
        san1Ca2.setIdSanBong(sanBong1.getId());
        san1Ca2.setIdCa(ca2.getId());
        san1Ca2.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal()); //0 chờ nhận sân
        san1Ca2.setThoiGianDat(localDateTime);
        san1Ca2.setNgayDenSan(LocalDate.now());
        san1Ca2.setGia(sanBong1.getGiaSan() + ca2.getGiaCa());
        san1Ca2.setId(getIdSanCa(sanBong1, ca2, dateId));
        san1Ca2.setUserId(kh1.getId());
        sanCaRepository.save(san1Ca2).getId();

        SanCa san2Ca1 = new SanCa();
        san2Ca1.setIdSanBong(sanBong2.getId());
        san2Ca1.setIdCa(ca1.getId());
        san2Ca1.setTrangThai(TrangThaiSanCa.DANG_DA.ordinal());
        san2Ca1.setThoiGianDat(localDateTime);
        san2Ca1.setNgayDenSan(LocalDate.now());
        san2Ca1.setGia(sanBong2.getGiaSan() + ca1.getGiaCa());
        san2Ca1.setId(getIdSanCa(sanBong2, ca1, dateId));
        san2Ca1.setUserId(kh1.getId());
        sanCaRepository.save(san2Ca1).getId();

        SanCa san2Ca2 = new SanCa();
        san2Ca2.setIdSanBong(sanBong2.getId());
        san2Ca2.setIdCa(ca2.getId());
        san2Ca2.setTrangThai(TrangThaiSanCa.CHO_NHAN_SAN.ordinal()); //0 chờ nhận sân
        san2Ca2.setThoiGianDat(localDateTime);
        san2Ca2.setNgayDenSan(LocalDate.now());
        san2Ca2.setGia(sanBong2.getGiaSan() + ca2.getGiaCa());
        san2Ca2.setId(getIdSanCa(sanBong2, ca2, dateId));
        san2Ca2.setUserId(kh1.getId());
        sanCaRepository.save(san2Ca2).getId();*/

        //end san ca


        PhuPhi phuPhi1 = new PhuPhi();
        phuPhi1.setMaPhuPhi("PP1");
        phuPhi1.setTenPhuPhi("HỎNG BÓNG");
        phuPhi1.setGiaPhuPhi(20000d);
        phuPhi1.setTrangThai(0);//0: ĐÃ TRUY THU PHỤ PHÍ
        phuPhi1.setId(phuPhiRepository.save(phuPhi1).getId());

        PhuPhi phuPhi2 = new PhuPhi();
        phuPhi2.setMaPhuPhi("PP2");
        phuPhi2.setTenPhuPhi("HỎNG ĐỒ THUÊ");
        phuPhi2.setGiaPhuPhi(20000d);
        phuPhi2.setTrangThai(0);
        phuPhi2.setId(phuPhiRepository.save(phuPhi2).getId());

        PhuPhi phuPhi3 = new PhuPhi();
        phuPhi3.setMaPhuPhi("PP3");
        phuPhi3.setTenPhuPhi("QUÁ GIỜ");
        phuPhi3.setGiaPhuPhi(20000d);
        phuPhi3.setTrangThai(0);
        phuPhi3.setId(phuPhiRepository.save(phuPhi3).getId());

        //END PHỤ PHÍ

       /* NuocUong nuocUong1 = new NuocUong();
        nuocUong1.setTenNuocUong("STING ĐỎ");
        nuocUong1.setSoLuong(11200);
        nuocUong1.setDonGia(8000d);
        nuocUong1.setImage("sting_do.jpg");
        nuocUong1.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong1.setId(nuocUongRepository.save(nuocUong1).getId());

        NuocUong nuocUong2 = new NuocUong();
        nuocUong2.setTenNuocUong("STING VÀNG");
        nuocUong2.setSoLuong(11200);
        nuocUong2.setDonGia(8000d);
        nuocUong2.setImage("sting_vang.jpg");
        nuocUong2.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong2.setId(nuocUongRepository.save(nuocUong2).getId());

        NuocUong nuocUong3 = new NuocUong();
        nuocUong3.setTenNuocUong("7 UP");
        nuocUong3.setSoLuong(11300);
        nuocUong3.setDonGia(8000d);
        nuocUong3.setImage("7up.jpg");
        nuocUong3.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong3.setId(nuocUongRepository.save(nuocUong3).getId());

        NuocUong nuocUong4 = new NuocUong();
        nuocUong4.setTenNuocUong("MIRINDA");
        nuocUong4.setSoLuong(11100);
        nuocUong4.setDonGia(10000d);
        nuocUong4.setImage("mirida.jpg");
        nuocUong4.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong4.setId(nuocUongRepository.save(nuocUong4).getId());

        NuocUong nuocUong5 = new NuocUong();
        nuocUong5.setTenNuocUong("NƯỚC KHOÁNG");
        nuocUong5.setSoLuong(1500);
        nuocUong5.setDonGia(5000d);
        nuocUong5.setImage("nuoc_khoang.png");
        nuocUong5.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong5.setId(nuocUongRepository.save(nuocUong5).getId());

        // END NƯỚC UỐNG

        DoThue doThue1 = new DoThue();
        doThue1.setTenDoThue("ÁO ĐỘI BÓNG");
        doThue1.setSoLuong(11200);
        doThue1.setDonGia(20000d);
        doThue1.setImage("dothue1.jpg");
        doThue1.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue1.setId(doThueRepository.save(doThue1).getId());

        DoThue doThue2 = new DoThue();
        doThue2.setTenDoThue("BÓNG ĐÁ");
        doThue2.setSoLuong(11200);
        doThue2.setDonGia(30000d);
        doThue2.setImage("bong_da1.jpg");
        doThue2.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue2.setId(doThueRepository.save(doThue2).getId());

        DoThue doThue3 = new DoThue();
        doThue3.setTenDoThue("GIÀY ĐÁ BÓNG");
        doThue3.setSoLuong(11200);
        doThue3.setDonGia(50000d);
        doThue3.setImage("giay-da-bong-2.jpg");
        doThue3.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue3.setId(doThueRepository.save(doThue3).getId());

        DoThue doThue4 = new DoThue();
        doThue4.setTenDoThue("BẢNG THỐNG KÊ");
        doThue4.setSoLuong(11200);
        doThue4.setDonGia(10000d);
        doThue4.setImage("tinh_diem2.jpg");
        doThue4.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue4.setId(doThueRepository.save(doThue4).getId());

        //END ĐỒ THUÊ*/

        /*DichVuSanBong dichVu1 = new DichVuSanBong();
        dichVu1.setIdDoThue(doThue1.getId());
        dichVu1.setIdNuocUong(nuocUong1.getId());
        dichVu1.setSoLuongNuocUong(5);
        dichVu1.setSoLuongDoThue(5);
        dichVu1.setDonGia((doThue1.getDonGia() * dichVu1.getSoLuongDoThue()) + (nuocUong1.getDonGia() * dichVu1.getSoLuongNuocUong()));
        dichVu1.setTrangThai(0); //1: DỊCH VỤ SẴN SÀNG
        dichVu1.setId(dichVuRepository.save(dichVu1).getId());

        DichVuSanBong dichVu2 = new DichVuSanBong();
        dichVu2.setIdDoThue(doThue2.getId());
        dichVu2.setIdNuocUong(nuocUong2.getId());
        dichVu2.setSoLuongNuocUong(1);
        dichVu2.setSoLuongDoThue(3);
        dichVu2.setDonGia((doThue2.getDonGia() * dichVu2.getSoLuongDoThue()) + (nuocUong2.getDonGia() * dichVu2.getSoLuongNuocUong()));
        dichVu2.setTrangThai(0); //1: DỊCH VỤ SẴN SÀNG
        dichVu2.setId(dichVuRepository.save(dichVu2).getId());

        DichVuSanBong dichVu3 = new DichVuSanBong();
        dichVu3.setIdDoThue(doThue3.getId());
        dichVu3.setIdNuocUong(nuocUong3.getId());
        dichVu3.setSoLuongNuocUong(15);
        dichVu3.setSoLuongDoThue(9);
        dichVu3.setDonGia((doThue3.getDonGia() * dichVu3.getSoLuongDoThue()) + (nuocUong3.getDonGia() * dichVu3.getSoLuongNuocUong()));
        dichVu3.setTrangThai(0); //1: DỊCH VỤ SẴN SÀNG
        dichVu3.setId(dichVuRepository.save(dichVu3).getId());

        DichVuSanBong dichVu4 = new DichVuSanBong();
        dichVu4.setIdDoThue(doThue4.getId());
        dichVu4.setIdNuocUong(nuocUong4.getId());
        dichVu4.setSoLuongNuocUong(3);
        dichVu4.setSoLuongDoThue(2);
        dichVu4.setDonGia((doThue4.getDonGia() * dichVu4.getSoLuongDoThue()) + (nuocUong4.getDonGia() * dichVu4.getSoLuongNuocUong()));
        dichVu4.setTrangThai(0); //1: DỊCH VỤ SẴN SÀNG
        dichVu4.setId(dichVuRepository.save(dichVu4).getId());*/

        // END DỊCH VỤ

        PhuPhiHoaDon phuPhiHoaDon1 = new PhuPhiHoaDon();
        phuPhiHoaDon1.setIdPhuPhi(phuPhi1.getId());
        phuPhiHoaDon1.setThoiGianTao(new Timestamp(System.currentTimeMillis()));
        phuPhiHoaDon1.setTrangThai(1); // PHỤ PHÍ HÓA ĐƠN SẴN SÀNG
        phuPhiHoaDon1.setId(phuPhiHoaDonRepository.save(phuPhiHoaDon1).getId());

        PhuPhiHoaDon phuPhiHoaDon2 = new PhuPhiHoaDon();
        phuPhiHoaDon2.setIdPhuPhi(phuPhi2.getId());
        phuPhiHoaDon2.setThoiGianTao(new Timestamp(System.currentTimeMillis()));
        phuPhiHoaDon2.setTrangThai(1); // PHỤ PHÍ HÓA ĐƠN SẴN SÀNG
        phuPhiHoaDon2.setId(phuPhiHoaDonRepository.save(phuPhiHoaDon2).getId());

        PhuPhiHoaDon phuPhiHoaDon3 = new PhuPhiHoaDon();
        phuPhiHoaDon3.setIdPhuPhi(phuPhi3.getId());
        phuPhiHoaDon3.setThoiGianTao(new Timestamp(System.currentTimeMillis()));
        phuPhiHoaDon3.setTrangThai(1); // PHỤ PHÍ HÓA ĐƠN SẴN SÀNG
        phuPhiHoaDon3.setId(phuPhiHoaDonRepository.save(phuPhiHoaDon3).getId());

        // END PHỤ PHÍ HÓA ĐƠN

        //LUAT SAN START
        LuatSan luatSan = new LuatSan();
        luatSan.setThongTin("<p><span style=\"color: rgb(136, 136, 136);\">Cảm ơn quý khách đã tin tưởng và sử dụng hệ thống sân bóng của chúng tôi. Chúng tôi rất vinh hạnh khi được phục vụ quý khách</span></p><p><span style=\"color: rgb(136, 136, 136);\">Để đảm bảo quý khách có thể đặt sân và sử dụng dịch vụ tại sân bóng của chúng tôi một cách an toàn và tốt nhất.</span></p><p><span style=\"color: rgb(136, 136, 136);\">Sân bóng Đồng Đế chúng tôi chuyên cung cấp các dịch vụ thể thao liên quan tới bóng đá. Sân bóng của chúng tôi đạt chuẩn FIFA mang lại sự an toàn khi thi đấu</span></p><p><span style=\"color: rgb(136, 136, 136);\">Website \"Đặt lịch sân bóng Đồng Đế\" của chúng tôi hoạt động 24/24 quý khách có thể chọn giờ và đặt sân bất cứ thời gian nào</span></p><p><br></p><h2 class=\"ql-align-center\"><strong style=\"color: rgb(29, 29, 39);\">Tuy nhiên chúng tôi có 1 số lưu ý cho quý khách như sau:</strong></h2><p class=\"ql-align-center\"><br></p><p><span style=\"color: rgb(136, 136, 136);\">1. Sau khi đặt sân trên hệ thống của chúng tôi, quý khách có 15 phút từ sau khi đặt sân để chuyển tiền cọc tới số tài khoản sân. Số tiền cọc sẽ tương đương 20% số tiền sân ( Không bao gồm dịch vụ ). Sau khi quý khách đã chuyển tiền. Hệ thống sẽ gửi thông báo đến quý khách để xác nhận quý khách đã đặt lịch thành công. Nhân viên chúng tôi sẽ liên hệ quý khách ngay sau khi khách hàng đặt sân ( Thời gian từ 7h - 23h hàng ngày ). Sau thời gian trên nếu quý khách đặt sân sẽ phải chờ vào khoảng thời gian nhân viên hoạt động để xác nhận sân đã được đặt.</span></p><p><span style=\"color: rgb(136, 136, 136);\">2. Dịch vụ của chúng tôi sẽ hỗ trợ quý khách đổi lịch sân trong khoảng thời gian trước 2 ngày lịch đặt của quý khách. Chúng tôi sẽ hủy sân nếu quý khách cố ý đổi gần ngày thi đấu. Trừ một số trường hợp đặc biệt.</span></p><p><span style=\"color: rgb(136, 136, 136);\">3. Khi đến sân quý khách vui lòng cung cấp mã QR để nhận sân. Nếu người đặt sân không có mặt tại đó quý khách vui lòng cung cấp số điện thoại người đặt để nhận sân. Mọi quyết định sẽ được đưa ra ngay thời điểm, và quyết định của chủ sân sẽ là quyết định cuối cùng</span></p><p><span style=\"color: rgb(136, 136, 136);\">4. Nếu quý khách có nhu cầu đặt sân gấp. Chúng tôi khuyến cáo quý khách nên liên hệ tới số 0356169620 để đặt sân trực tiếp thông qua nhân viên trực tại sân.</span></p><p><span style=\"color: rgb(136, 136, 136);\">5. Nếu quý khách đã đặt cọc và tự ý hủy sân. Số tiền quý khách đã cọc sẽ không được thanh toán lại trong mọi trường hợp.</span></p><p><span style=\"color: rgb(136, 136, 136);\">6. Mọi sự việc tại sân quyết định của chủ sân là quyết định cuối cùng nhất</span></p><p><span style=\"color: rgb(136, 136, 136);\">7. LƯU Ý: Chúng tôi khuyến cáo quý khách đặt sân trên website của chúng tôi để có thể nắm được các thông tin về các ca, số sân trống dễ dàng phục vụ cho quý khách</span></p>");
        luatSan.setTrangThai(0);
        luatSanRepository.save(luatSan);
        //LUAT SAN END
    }

    private String getIdSanCa(SanBong sanBong, Ca ca, String dateId) {
        return sanBong.getId() + '+' + ca.getId() + "+" + sanBong.getIdLoaiSan() + "+" + dateId;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}