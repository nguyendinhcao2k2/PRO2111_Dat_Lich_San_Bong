package com.example.generate_DB;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
import com.example.pro2111_dat_lich_san_bong.repository.DichVuRepository;
import com.example.pro2111_dat_lich_san_bong.repository.DoThueRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HangKhachHangRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.repository.LichSuChatRepository;
import com.example.pro2111_dat_lich_san_bong.repository.LoaiSanRepository;
import com.example.pro2111_dat_lich_san_bong.repository.NuocUongRepository;
import com.example.pro2111_dat_lich_san_bong.repository.PhuPhiHoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.repository.PhuPhiRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HinhThucThanhToanRepository;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author phongtt35
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

    private final boolean IS_RELEASE = false;

    @Override
    public void run(String... args) throws Exception {


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
        ViTienCoc viTienCocKH1 = new ViTienCoc();
        viTienCocKH1.setTaiKhoanVi("004444444444");
        viTienCocKH1.setLoaiTien("VND");
        viTienCocKH1.setTrangThai(0);
        viTienCocKH1.setThoiGianTao(Timestamp.valueOf("2023-09-17 10:09:00"));
        viTienCocKH1.setSoTien(200000.0);
        viTienCocKH1.setId(viTienRepository.save(viTienCocKH1).getId());

        ViTienCoc viTienCocKH2 = new ViTienCoc();
        viTienCocKH2.setTaiKhoanVi("007777777777");
        viTienCocKH2.setLoaiTien("VND");
        viTienCocKH2.setTrangThai(0);
        viTienCocKH2.setThoiGianTao(Timestamp.valueOf("2023-09-17 10:09:00"));
        viTienCocKH2.setSoTien(200000.0);
        viTienCocKH2.setId(viTienRepository.save(viTienCocKH2).getId());


        //end vi tien

        //start account

        Account kh1 = new Account();
        kh1.setEmail("taikhoan1@gamil.com");
        kh1.setSoDienThoai("0312345678");
        kh1.setDisplayName("tai khoản 1");
        kh1.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        kh1.setTrangThai(0); //trang thái 0: hoạt động
        kh1.setIdChucVu(khachHangRole.getId());
        kh1.setIdViTienCoc(viTienCocKH1.getId());
        kh1.setTaiKhoan("account1");
        kh1.setId(accountRepository.save(kh1).getId());


        Account kh2 = new Account();
        kh2.setEmail("taikhoan2@gamil.com");
        kh2.setSoDienThoai("0312345678");
        kh2.setDisplayName("tai khoản 2");
        kh2.setMatKhau("$2a$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2"); // pass: 1111
        kh2.setTrangThai(0); //trang thái 0: hoạt động
        kh2.setIdChucVu(khachHangRole.getId());
        kh2.setIdViTienCoc(viTienCocKH2.getId());
        kh2.setTaiKhoan("account2");
        kh2.setId(accountRepository.save(kh2).getId());

        //end account
        //start ca
        Ca ca1 = new Ca();
        ca1.setTenCa("Ca 1");
        ca1.setGiaCa(200000.0);
        ca1.setThoiGianBatDau(Time.valueOf("06:00:00"));
        ca1.setThoiGianKetThuc(Time.valueOf("07:30:00"));
        ca1.setTrangThai(0);
        ca1.setId(caRepository.save(ca1).getId());

        Ca ca2 = new Ca();
        ca2.setTenCa("Ca 2");
        ca2.setGiaCa(200000.0);
        ca2.setThoiGianBatDau(Time.valueOf("07:30:00"));
        ca2.setThoiGianKetThuc(Time.valueOf("09:00:00"));
        ca2.setTrangThai(0);
        ca2.setId(caRepository.save(ca2).getId());

        Ca ca3 = new Ca();
        ca3.setTenCa("Ca 3");
        ca3.setGiaCa(200000.0);
        ca3.setThoiGianBatDau(Time.valueOf("09:00:00"));
        ca3.setThoiGianKetThuc(Time.valueOf("10:30:00"));
        ca3.setTrangThai(0);
        ca3.setId(caRepository.save(ca3).getId());

        Ca ca4 = new Ca();
        ca4.setTenCa("Ca 4");
        ca4.setGiaCa(150000.0);
        ca4.setThoiGianBatDau(Time.valueOf("10:30:00"));
        ca4.setThoiGianKetThuc(Time.valueOf("12:00:00"));
        ca4.setTrangThai(0);
        ca4.setId(caRepository.save(ca4).getId());

        Ca ca5 = new Ca();
        ca5.setTenCa("Ca 5");
        ca5.setGiaCa(150000.0);
        ca5.setThoiGianBatDau(Time.valueOf("12:00:00"));
        ca5.setThoiGianKetThuc(Time.valueOf("13:30:00"));
        ca5.setTrangThai(0);
        ca5.setId(caRepository.save(ca5).getId());

        Ca ca6 = new Ca();
        ca6.setTenCa("Ca 6");
        ca6.setGiaCa(150000.0);
        ca6.setThoiGianBatDau(Time.valueOf("13:30:00"));
        ca6.setThoiGianKetThuc(Time.valueOf("15:00:00"));
        ca6.setTrangThai(0);
        ca6.setId(caRepository.save(ca6).getId());

        Ca ca7 = new Ca();
        ca7.setTenCa("Ca 7");
        ca7.setGiaCa(250000.0);
        ca7.setThoiGianBatDau(Time.valueOf("15:00:00"));
        ca7.setThoiGianKetThuc(Time.valueOf("16:30:00"));
        ca7.setTrangThai(0);
        ca7.setId(caRepository.save(ca7).getId());

        Ca ca8 = new Ca();
        ca8.setTenCa("Ca 8");
        ca8.setGiaCa(300000.0);
        ca8.setThoiGianBatDau(Time.valueOf("16:30:00"));
        ca8.setThoiGianKetThuc(Time.valueOf("18:00:00"));
        ca8.setTrangThai(0);
        ca8.setId(caRepository.save(ca8).getId());

        Ca ca9 = new Ca();
        ca9.setTenCa("Ca 9");
        ca9.setGiaCa(400000.0);
        ca9.setThoiGianBatDau(Time.valueOf("18:00:00"));
        ca9.setThoiGianKetThuc(Time.valueOf("19:30:00"));
        ca9.setTrangThai(0);
        ca9.setId(caRepository.save(ca9).getId());

        Ca ca10 = new Ca();
        ca10.setTenCa("Ca 10");
        ca10.setGiaCa(400000.0);
        ca10.setThoiGianBatDau(Time.valueOf("19:30:00"));
        ca10.setThoiGianKetThuc(Time.valueOf("21:00:00"));
        ca10.setTrangThai(0);
        ca10.setId(caRepository.save(ca10).getId());

        Ca ca11 = new Ca();
        ca11.setTenCa("Ca 11");
        ca11.setGiaCa(400000.0);
        ca11.setThoiGianBatDau(Time.valueOf("21:00:00"));
        ca11.setThoiGianKetThuc(Time.valueOf("22:30:00"));
        ca11.setTrangThai(0);
        ca11.setId(caRepository.save(ca11).getId());

        Ca ca12 = new Ca();
        ca12.setTenCa("Ca 12");
        ca12.setGiaCa(200000.0);
        ca12.setThoiGianBatDau(Time.valueOf("22:30:00"));
        ca12.setThoiGianKetThuc(Time.valueOf("00:00:00"));
        ca12.setTrangThai(0);
        ca12.setId(caRepository.save(ca12).getId());

        //end ca
        //Loai san start

        LoaiSan loaiSan1 = new LoaiSan();
        loaiSan1.setTenLoaiSan("Sân 5");
        loaiSan1.setMoTa("Sân giành cho mỗi đội 5 người ");
        loaiSan1.setId(loaiSanRepository.save(loaiSan1).getId());

        LoaiSan loaiSan2 = new LoaiSan();
        loaiSan2.setTenLoaiSan("Sân 7");
        loaiSan2.setMoTa("Sân giành cho mỗi đội 7 người ");
        loaiSan2.setId(loaiSanRepository.save(loaiSan2).getId());

        LoaiSan loaiSan3 = new LoaiSan();
        loaiSan3.setTenLoaiSan("Sân 11");
        loaiSan3.setMoTa("Sân giành cho mỗi đội 11 người ");
        loaiSan3.setId(loaiSanRepository.save(loaiSan3).getId());

        //Loai san end

        //San bong start
        SanBong sanBong1 = new SanBong();
        sanBong1.setTenSanBong("Sân 1");
        sanBong1.setGiaSan(100000.0);
        sanBong1.setIdLoaiSan(loaiSan1.getId());
        sanBong1.setTrangThai(0);
        sanBong1.setId(sanBongRepository.save(sanBong1).getId());

        SanBong sanBong2 = new SanBong();
        sanBong2.setTenSanBong("Sân 2");
        sanBong2.setGiaSan(100000.0);
        sanBong2.setIdLoaiSan(loaiSan2.getId());
        sanBong2.setTrangThai(0);
        sanBong2.setId(sanBongRepository.save(sanBong2).getId());

        SanBong sanBong3 = new SanBong();
        sanBong3.setTenSanBong("Sân 3");
        sanBong3.setGiaSan(250000.0);
        sanBong3.setIdLoaiSan(loaiSan2.getId());
        sanBong3.setTrangThai(0);
        sanBong3.setId(sanBongRepository.save(sanBong3).getId());

        SanBong sanBong4 = new SanBong();
        sanBong4.setTenSanBong("Sân 4");
        sanBong4.setGiaSan(250000.0);
        sanBong4.setIdLoaiSan(loaiSan2.getId());
        sanBong4.setTrangThai(0);
        sanBong4.setId(sanBongRepository.save(sanBong4).getId());

        SanBong sanBong5 = new SanBong();
        sanBong5.setTenSanBong("Sân 5");
        sanBong5.setGiaSan(250000.0);
        sanBong5.setIdLoaiSan(loaiSan2.getId());
        sanBong5.setTrangThai(0);
        sanBong5.setId(sanBongRepository.save(sanBong5).getId());

        SanBong sanBong6 = new SanBong();
        sanBong6.setTenSanBong("Sân 6");
        sanBong6.setGiaSan(250000.0);
        sanBong6.setIdLoaiSan(loaiSan2.getId());
        sanBong6.setTrangThai(0);
        sanBong6.setId(sanBongRepository.save(sanBong6).getId());

        SanBong sanBong7 = new SanBong();
        sanBong7.setTenSanBong("Sân 7");
        sanBong7.setGiaSan(500000.0);
        sanBong7.setIdLoaiSan(loaiSan3.getId());
        sanBong7.setTrangThai(0);
        sanBong7.setId(sanBongRepository.save(sanBong7).getId());

        //end san bong
        Date dateNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        SanCa san1Ca1 = new SanCa();
        san1Ca1.setIdSanBong(sanBong1.getId());
        san1Ca1.setIdCa(ca1.getId());
        san1Ca1.setTrangThai(0); //0 trống
        san1Ca1.setThoiGianTao(Timestamp.valueOf(format.format(dateNow)));
        san1Ca1.setGia(sanBong1.getGiaSan() + ca1.getGiaCa());
        san1Ca1.setId(sanCaRepository.save(san1Ca1).getId());

        SanCa san1Ca2 = new SanCa();
        san1Ca2.setIdSanBong(sanBong1.getId());
        san1Ca2.setIdCa(ca2.getId());
        san1Ca2.setTrangThai(0); //0 trống
        san1Ca2.setThoiGianTao(Timestamp.valueOf(format.format(dateNow)));
        san1Ca2.setGia(sanBong1.getGiaSan() + ca2.getGiaCa());
        san1Ca2.setId(sanCaRepository.save(san1Ca2).getId());


        SanCa san2Ca1 = new SanCa();
        san2Ca1.setIdSanBong(sanBong2.getId());
        san2Ca1.setIdCa(ca1.getId());
        san2Ca1.setTrangThai(0); //0 trống
        san2Ca1.setThoiGianTao(Timestamp.valueOf(format.format(dateNow)));
        san2Ca1.setGia(sanBong2.getGiaSan() + ca1.getGiaCa());
        san2Ca1.setId(sanCaRepository.save(san2Ca1).getId());


        SanCa san2Ca2 = new SanCa();
        san2Ca2.setIdSanBong(sanBong2.getId());
        san2Ca2.setIdCa(ca2.getId());
        san2Ca2.setTrangThai(0); //0 trống
        san2Ca2.setThoiGianTao(Timestamp.valueOf(format.format(dateNow)));
        san2Ca2.setGia(sanBong2.getGiaSan() + ca2.getGiaCa());
        san2Ca2.setId(sanCaRepository.save(san2Ca2).getId());


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

        NuocUong nuocUong1 = new NuocUong();
        nuocUong1.setTenNuocUong("STING ĐỎ");
        nuocUong1.setSoLuong(200);
        nuocUong1.setDonGia(8000d);
        nuocUong1.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong1.setId(nuocUongRepository.save(nuocUong1).getId());

        NuocUong nuocUong2 = new NuocUong();
        nuocUong2.setTenNuocUong("STING VÀNG");
        nuocUong2.setSoLuong(200);
        nuocUong2.setDonGia(8000d);
        nuocUong2.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong2.setId(nuocUongRepository.save(nuocUong2).getId());

        NuocUong nuocUong3 = new NuocUong();
        nuocUong3.setTenNuocUong("7 UP");
        nuocUong3.setSoLuong(300);
        nuocUong3.setDonGia(8000d);
        nuocUong3.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong3.setId(nuocUongRepository.save(nuocUong3).getId());

        NuocUong nuocUong4 = new NuocUong();
        nuocUong4.setTenNuocUong("MIRIDA");
        nuocUong4.setSoLuong(100);
        nuocUong4.setDonGia(10000d);
        nuocUong4.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong4.setId(nuocUongRepository.save(nuocUong4).getId());

        NuocUong nuocUong5 = new NuocUong();
        nuocUong5.setTenNuocUong("NƯỚC KHOÁNG");
        nuocUong5.setSoLuong(500);
        nuocUong5.setDonGia(5000d);
        nuocUong5.setTrangThai(0); // 0: ĐANG BÁN 1: TẠM DỪNG
        nuocUong5.setId(nuocUongRepository.save(nuocUong5).getId());

        // END NƯỚC UỐNG

        DoThue doThue1 = new DoThue();
        doThue1.setTenDoThue("ÁO ĐỘI BÓNG");
        doThue1.setSoLuong(200);
        doThue1.setDonGia(20000d);
        doThue1.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue1.setId(doThueRepository.save(doThue1).getId());

        DoThue doThue2 = new DoThue();
        doThue2.setTenDoThue("BÓNG ĐÁ");
        doThue2.setSoLuong(200);
        doThue2.setDonGia(30000d);
        doThue2.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue2.setId(doThueRepository.save(doThue2).getId());

        DoThue doThue3 = new DoThue();
        doThue3.setTenDoThue("GIÀY ĐÁ BÓNG");
        doThue3.setSoLuong(200);
        doThue3.setDonGia(50000d);
        doThue3.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue3.setId(doThueRepository.save(doThue3).getId());

        DoThue doThue4 = new DoThue();
        doThue4.setTenDoThue("BẢNG THỐNG KÊ");
        doThue4.setSoLuong(200);
        doThue4.setDonGia(10000d);
        doThue4.setTrangThai(0); //0:CÒN ĐỒ THUÊ -- 1: HẾT ĐỒ THUÊ
        doThue4.setId(doThueRepository.save(doThue4).getId());

        //END ĐỒ THUÊ

        DichVuSanBong dichVu1 = new DichVuSanBong();
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
        dichVu4.setId(dichVuRepository.save(dichVu4).getId());

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
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}