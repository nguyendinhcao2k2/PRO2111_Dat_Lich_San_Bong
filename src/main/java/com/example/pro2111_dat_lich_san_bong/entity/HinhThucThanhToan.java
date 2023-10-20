package com.example.pro2111_dat_lich_san_bong.entity;

import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "hinh_thuc_thanh_toan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HinhThucThanhToan {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "id_hoa_don_san_ca")
    private String idHoaDonSanCa;
    @Column(name = "hinh_thuc_thanh_toan")
    private LoaiHinhThanhToan loaiHinhThanhToan;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
