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
@Table(name = "binh_luan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinhLuan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "noi_dung_binh_luan", length = 500)
    private String noiDungBinhLuan;
    @Column(name = "thoi_gian_binh_luan", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianBinhLuan;
    @Column(name = "id_account")
    private String idAccount;

    @Column(name = "id_san_bong")
    private String idSanBong;

}
