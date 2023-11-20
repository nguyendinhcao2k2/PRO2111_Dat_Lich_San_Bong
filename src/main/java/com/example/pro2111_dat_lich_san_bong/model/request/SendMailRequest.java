package com.example.pro2111_dat_lich_san_bong.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thepvph20110
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequest {

    private String title;
    private String nguoiNhanMail;
    private String qrCodeData;
    private String timeStart;
    private String timeEnd;
    private String nguoiDat;
    private String ngayDat;
    private String ngayCheckIn;
    private Double giaTien;
}
