package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class GeneraterQRCode {

    public String generateQRCode(String value) throws WriterException, IOException {
        BitMatrix bitMatrix = new QRCodeWriter().encode(value, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        byte[] qrcodeData = outputStream.toByteArray();
        StringBuilder hexString = new StringBuilder();
        String qrCodeHex = Base64.getEncoder().encodeToString(qrcodeData);
        return qrCodeHex;
    }
}
