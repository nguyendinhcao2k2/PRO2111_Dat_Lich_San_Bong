package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "ma_giam_gia")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaGiamGia {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "phan_tram")
    private Integer phanTram;

    @Column(name = "ten_ma_giam_gia")
    private String tenMaGiamGia;

    @Column(name = "ngay_bat_dau", columnDefinition = "TIMESTAMP")
    private Timestamp ngayBatDau;

    @Column(name = "ngay_ket_thuc", columnDefinition = "TIMESTAMP")
    private Timestamp ngayKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "id_khach_hang")
    private String idKhachHang;

    @Column(name = "id_account")
    private String idAccount;

}
