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
import org.hibernate.annotations.Nationalized;

import java.sql.Date;
import java.sql.Time;

/**
 * @author caodinh
 */
@Entity
@Table(name = "ca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ca {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ten_ca", length = 1000)
    @Nationalized
    private String tenCa;

    @Column(columnDefinition = "time")
    private Time thoiGianBatDau;

    @Column(columnDefinition = "time")
    private Time thoiGianKetThuc;

    @Column(name = "gia_ca")
    private Double giaCa;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
