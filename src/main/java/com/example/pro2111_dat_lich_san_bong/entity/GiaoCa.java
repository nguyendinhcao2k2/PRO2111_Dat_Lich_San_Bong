package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "GiaoCa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoCa {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    private String id;
    @Column(name = "thoi_gian_nhan_ca", columnDefinition = "TIMESTAMP")
    private String thoiGianNhanCa;
    @Column(name = "thoi_gian_giao_ca", columnDefinition = "TIMESTAMP")
    private String thoiGianGiaoCa;
    @Column(name = "id_nhan_vien_trong_ca")
    private String idNhanVienTrongCa;
    @Column(name = "id_nhan_vien_ca_tiep_theo")
    private String idNhanVienCaTiepTheo;
    @Column(name = "tien_ban_dau")
    private double tienBanDau;
    @Column(name = "tong_tien_mat")
    private double tongTienMat;
    @Column(name = "tong_tien_khac")
    private double tongTienKhac;
    @Column(name = "tong_tien_trong_ca")
    private double tongTienTrongCa;
    @Column(name = "tien_phat_sinh")
    private double tienPhatSinh;
    @Column(columnDefinition = "nvarchar(255)",name = "ghi_chu_phat_sinh")
    private String ghiChuPhatSinh;
    @Column(name = "tong_tien_mat_ca_truoc")
    private float tongTienMatCaTruoc;
    @Column(name = "thoi_gian_reset", columnDefinition = "TIMESTAMP")
    private String thoiGianReset;
    @Column(name = "id_account")
    private String idAccount;
    @Column(name = "tong_tien_mat_rut")
    private double tongTienMatRut;
    @Column(name = "trang_thai")
    private int trangThai;

}
