package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "thoi_giam_check_in", columnDefinition = "TIMESTAMP")
    private Time thoiGianCheckIn;
    @Column(name = "ghi_chu")
    private String ghiChu;
    @Column(name = "tong_tien")
    private Double tongTien;
    @Column(name = "id_dich_vu_sam_bong")
    private String idDichVuSanBong;
    @Column(name = "id_hoa_don")
    private String idHoaDon;
    @Column(name = "id_san_ca")
    private String idSanCa;
    @Column(name = "trang_thai")
    private Integer trangThai;
    @Column(name = "ma_qr")
    private String maQR;
    @Column(name = "id_su_dung_tien_coc")
    private String idSuDungTienCoc;
    @Column(name = "id_phu_phi_hoa_don")
    private String idPhuPhiHoaDon;

}
