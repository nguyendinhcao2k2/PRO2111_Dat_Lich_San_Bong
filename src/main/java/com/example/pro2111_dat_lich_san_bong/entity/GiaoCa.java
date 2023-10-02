package com.example.pro2111_dat_lich_san_bong.entity;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


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
    private Timestamp thoiGianNhanCa;
    @Column(name = "thoi_gian_ket_ca", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianKetCa;
    @Column(name = "id_nhan_vien_trong_ca", columnDefinition = "varchar(36)")
    private String idNhanVienTrongCa;
    @Column(name = "id_nhan_vien_ca_tiep_theo", columnDefinition = "varchar(36)")
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
    private double tongTienMatCaTruoc;
    @Column(name = "thoi_gian_reset", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianReset;
    @Column(name = "tong_tien_mat_rut")
    private double tongTienMatRut;
    @Column(name = "id_account")
    private String idAccount;
    @Column(name = "trang_thai")
    private TrangThaiGiaoCa trangThai;

}
