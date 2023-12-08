package com.example.pro2111_dat_lich_san_bong.entity;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author caodinh
 */
@Entity
@Table(name = "hoa_don_san_ca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonSanCa {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "ngay_den_san", columnDefinition = "TIMESTAMP")
    private LocalDate ngayDenSan;
    @Column(name = "thoi_giam_check_in")
    private Time thoiGianCheckIn;
    @Column(name = "tien_san")
    private Double tienSan = 0.0;
    @Column(name = "tong_tien_hoa_don_san_ca")
    private Double tongTienHoaDonSanCa = 0.0;
    @Column(name = "id_hoa_don")
    private String idHoaDon;
    @Column(name = "id_san_ca")
    private String idSanCa;
    @Column(name = "trang_thai")
    private Integer trangThai;
    @Column(name = "ma_qr")
    private String maQR;
    @Column(name = "ngay_thanh_toan")
    private Timestamp ngayThanhToan;
    @Column(name = "id_lich_su_vi_tien")
    private String idLichSuViTien;
    @Column(name = "id_giao_ca")
    private String idGiaoCa;
    @Column(name = "dem_so_lan_doi_lich")
    private Integer countLich = 0;
    @Column(name = "tien_coc_thua")
    private Double tienCocThua = 0.0;
}
