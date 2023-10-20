package com.example.pro2111_dat_lich_san_bong.entity;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLoaiBienDong;
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

import java.time.LocalDateTime;

/**
 * @author caodinh
 */
@Entity
@Table(name = "lich_su_vi_tien")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LichSuViTien {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "nguoi_nhan")
    private String nguoiNhan;
    @Column(name = "so_tien")
    private Double soTien;
    @Column(name = "loai_tien")
    private String loaiTien;
    @Column(name = "thoi_gian")
    private LocalDateTime thoiGian;
    @Column(name = "loai_bien_dong")
    private TrangThaiLoaiBienDong loaiBienDong;
    @Column(name = "tao_khoan_vi")
    private String taiKhoanVi;
    @Column(name = "id_vi_tien_coc")
    private String idViTienCoc;
    @Column(name = "trang_thai")
    private String trangThai;

}
