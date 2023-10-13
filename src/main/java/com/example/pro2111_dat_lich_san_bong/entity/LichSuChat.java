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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "lich_su_chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichSuChat {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "noi_dung")
    @Nationalized
    private String noiDung;

    @Column(name = "thoi_gian_tao", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianTao;

    @Column(name = "id_account")
    private String idAccount;

    @Column(name = "id_nguoi_nhan")
    private String idNguoiNhan;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
