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

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "bien_dong_vi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BienDongVi  {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "nguoi_nhan")
    private Number nguoiNhan;

    @Column(name = "so_tien")
    private Double soTien;

    @Column(name = "loai_tien")
    private String loaiTien;

    @Column(name = "trang_thai")
    private int trangThai;

    @Column(name = "thoi_gian")
    private Date thoiGian;

    @Column(name = "loai_bien_dong")
    private int loaiBienDong;

    @Column(name = "tai_khoan_vi")
    private double taiKhoanVi;

}
