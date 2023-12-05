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

/**
 * @author caodinh
 */

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "email", length = 225)
    private String email;

    @Column(name = "image",columnDefinition = "LONGTEXT")
    private String image;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau", length = 2000)
    private String matKhau;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "display_name")
    @Nationalized
    private String displayName;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "id_chuc_vu")
    private String idChucVu;

    @Column(name = "id_hang_khach_hang")
    private String idHangKhachHang;


}
