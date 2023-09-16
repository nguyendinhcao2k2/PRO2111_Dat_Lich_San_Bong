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

/**
 * @author caodinh
 */

@Entity
@Table(name = "san_ca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanCa {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "id_san_bong")
    private String idSanBong;

    @Column(name = "id_ca")
    private String idCa;

    @Column(name = "thoi_gian_tao")
    private Date thoiGianTao;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
