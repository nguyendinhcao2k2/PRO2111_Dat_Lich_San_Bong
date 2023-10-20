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

/**
 * @author caodinh
 */

@Entity
@Table(name = "phu_phi_hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuPhiHoaDon {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "id_hoa_don_san_ca")
    private String idHoaDonSanCa;
    @Column(name = "id_phu_phi")
    private String idPhuPhi;
    @Column(name = "thoi_gian_tao", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianTao;
    @Column(name = "trang_thai")
    private Integer trangThai;

}
