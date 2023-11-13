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

/**
 * @author caodinh
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dich_vu_san_bong")
public class DichVuSanBong {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "so_luong_do_thue")
    private Integer soLuongDoThue;

    @Column(name = "so_luong_nuoc_uong")
    private Integer soLuongNuocUong;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "id_nuoc_uong")
    private String idNuocUong;

    @Column(name = "id_do_thue")
    private String idDoThue;

    @Column(name = "id_hoa_don_san_ca")
    private String idHoaDonSanCa;

}
