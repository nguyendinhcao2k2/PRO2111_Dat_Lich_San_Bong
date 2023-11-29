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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */
@Entity
@Table(name = "thoi_gian_dat_lich")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThoiGianDatLich {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "ngay_dat")
    private LocalDateTime ngayDat;
    @Column(name = "id_loai_san")
    private String idLoaiSan;
    @Column(name = "id_ca")
    private String idCa;
    @Column(name = "id_hoa_don")
    private String idHoaDon;

    @Column(name = "gia_dat_lich")
    private Double giaDatLich;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
