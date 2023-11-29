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
import java.time.LocalDate;
import java.time.LocalDateTime;

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
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "id_san_bong")
    private String idSanBong;

    @Column(name = "id_ca")
    private String idCa;

    @Column(name = "thoi_gian_dat", columnDefinition = "TIMESTAMP")
    private LocalDateTime thoiGianDat;

    @Column(name = "ngay_den_san", columnDefinition = "TIMESTAMP")
    private LocalDate ngayDenSan;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "user_id")
    private String userId;

}
