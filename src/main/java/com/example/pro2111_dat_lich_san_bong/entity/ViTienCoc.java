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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "vi_tien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViTienCoc {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "thoi_gian_tao", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianTao;

    @Column(name = "so_tien")
    private Double soTien;

    @Column(name = "loai_tien")
    private String loaiTien;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_giao_dich")
    private String soGiaoDich;

    @Column(name = "id_hoa_don")
    private String idHoaDon;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "type_payment")
    private Integer typePayment;
}
