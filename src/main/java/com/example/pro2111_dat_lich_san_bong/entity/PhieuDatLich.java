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

import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "phieu_dat_lich")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatLich {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "gio_bat_dau", columnDefinition = "TIMESTAMP")
    private LocalDateTime gioBatDau;

    @Column(name = "gio_ket_thuc", columnDefinition = "TIMESTAMP")
    private LocalDateTime gioKetThuc;

    @Column(name = "thoi_gian_check_in", columnDefinition = "TIMESTAMP")
    private LocalDateTime thoiGianCheckIn;

    @Column(name = "tong_tien_san_ca")
    private double tongTienSanCa;

    @Column(name = "trang_thai")
    private int trangThai;

}