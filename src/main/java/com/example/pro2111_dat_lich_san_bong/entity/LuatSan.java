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
@Entity
@Table(name = "luat_san_bong")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LuatSan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;
    @Column(name = "thongTin",columnDefinition = "LONGTEXT")
    private String thongTin;
    @Column(name = "id_hoa_don")
    private String idHoaDon;
    @Column(name = "so_lan_dat")
    private Integer soLanDat;
    @Column(name = "so_tien_giam")
    private Integer soTienGiam;
    @Column(name = "trang_thai")
    private Integer trangThai;

}
