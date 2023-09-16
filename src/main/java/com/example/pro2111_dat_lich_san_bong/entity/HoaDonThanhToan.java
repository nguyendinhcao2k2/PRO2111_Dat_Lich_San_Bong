package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

/**
 * @author caodinh
 */
@Entity
@Table(name = "hoa_don_thanh_toan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonThanhToan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "tong_tien")
    private double tongTien;

    @Column(name = "ghi_chu", length = 4000)
    @Nationalized
    private String ghiChu;

    @Column(name = "trang_thai")
    private int trangThai;

    @Column(name = "id_thanh_toan")
    private String idThanhToan;

    @Column(name = "id_hoa_don")
    private String idHoaDon;

}