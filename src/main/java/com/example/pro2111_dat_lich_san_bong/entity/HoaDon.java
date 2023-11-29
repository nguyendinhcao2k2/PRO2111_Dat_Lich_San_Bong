package com.example.pro2111_dat_lich_san_bong.entity;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLuatSan;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author caodinh
 */

@Entity
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "id_account")
    private String idAccount;
    @Column(name = "ngay_tao", columnDefinition = "TIMESTAMP")
    private LocalDateTime ngayTao;
    @Column(name = "id_account_confirm")
    private String idAccountConfirm;
    @Column(name = "ngay_xac_nhan_dat_san", columnDefinition = "TIMESTAMP")
    private LocalDateTime ngayXacNhanDatSan;
    @Column(name = "so_dien_thoai_nguoi_dat")
    private String soDienThoaiNguoiDat;
    @Column(name = "tien_coc")
    private Double tienCoc;
    @Column(name = "ma_tien_coc")
    private String maTienCoc;
    @Column(name = "tong_tien")
    private Double tongTien;
    @Column(name = "email")
    private String email;
    @Column(name = "ten_nguoi_dat")
    private String tenNguoiDat;
    @Column(name = "id_luat_san_bong")
    private String idLuatSanBong;
    @Column(name = "trang_thai")
    private Integer trangThai;
    @Column(name = "trang_thai_luat_san")
    private TrangThaiLuatSan trangThaiLuatSan;
    @Column(name = "ghi_chu")
    private String ghiChu;
}
