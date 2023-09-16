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
import org.hibernate.annotations.Nationalized;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon  {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "ngay_thanh_toan", columnDefinition = "TIMESTAMP")
    private Date ngayThanhToan;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "ghi_chu")
    @Nationalized
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "id_san_ca")
    private String idSanCa;

    @Column(name = "id_phieu_dat_lich")
    private String idPhieuDatLich;

    @Column(name = "id_dich_vu")
    private String idDichVu;

    @Column(name = "id_phu_phi_hoa_don")
    private String idPhuPhiHoaDon;

    @Column(name = "id_vi_tien")
    private String idViTien;

}
