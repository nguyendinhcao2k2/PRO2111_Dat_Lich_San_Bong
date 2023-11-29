package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.model.request.SendMailRequest;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class SendMailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public String sendEmail(SendMailRequest request) {

        String title = request.getTitle();
        String nguoiNhanMail = request.getNguoiNhanMail();
        String qrCodeData = request.getQrCodeData();
        String timeStart= request.getTimeStart();
        String timeEnd = request.getTimeEnd();
        String nguoiDat = request.getNguoiDat();
        String ngayDat = request.getNgayDat();
        String ngayCheckIn = request.getNgayCheckIn();
        Double giaTien = request.getGiaTien();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);


                    helper.setFrom(fromEmail);
                    helper.setTo(nguoiNhanMail);
                    helper.setSubject(title);

                    // Đọc tệp HTML chứa mã QR code
                    String htmlContent = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"utf-8\" />\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                            "    <title>"+title+"</title>\n" +
                            "    <link\n" +
                            "            href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\"\n" +
                            "            rel=\"stylesheet\"\n" +
                            "            integrity=\"sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN\"\n" +
                            "            crossorigin=\"anonymous\"\n" +
                            "    />\n" +
                            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js\"></script>\n" +
                            "    <style>\n" +
                            "        html,\n" +
                            "        body {\n" +
                            "            height: 100%;\n" +
                            "            margin: 0;\n" +
                            "            padding: 0;\n" +
                            "            overflow: hidden;\n" +
                            "        }\n" +
                            "\n" +
                            "        html {\n" +
                            "            box-sizing: border-box;\n" +
                            "        }\n" +
                            "\n" +
                            "        /* body {\n" +
                            "          background: linear-gradient(\n" +
                            "            45deg,\n" +
                            "            rgba(204, 31, 48, 1) 0%,\n" +
                            "            rgba(123, 53, 132, 1) 100%\n" +
                            "          );\n" +
                            "        } */\n" +
                            "\n" +
                            "        .page:after {\n" +
                            "            content: \"\";\n" +
                            "            display: block;\n" +
                            "            position: fixed;\n" +
                            "            height: 100%;\n" +
                            "            width: 100%;\n" +
                            "            left: 0;\n" +
                            "            top: 0;\n" +
                            "            background: rgba(0, 0, 0, 0.5);\n" +
                            "            z-index: -1;\n" +
                            "        }\n" +
                            "\n" +
                            "        *,\n" +
                            "        *:before,\n" +
                            "        *:after {\n" +
                            "            box-sizing: inherit;\n" +
                            "        }\n" +
                            "\n" +
                            "        body {\n" +
                            "            font-family: Helvetica, Arial, sans-serif;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass.full {\n" +
                            "            height: 520px;\n" +
                            "            width: 320px;\n" +
                            "            position: relative;\n" +
                            "            background: #ffffff;\n" +
                            "            padding: 0 0 20px 0;\n" +
                            "            position: absolute;\n" +
                            "            left: 50%;\n" +
                            "            top: 50%;\n" +
                            "            transform: translate(-50%, -50%);\n" +
                            "            border: 1px solid #cc1f2f;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass header {\n" +
                            "            background: #cc1f2f;\n" +
                            "            padding: 15px 10px;\n" +
                            "            color: #fff;\n" +
                            "            text-align: center;\n" +
                            "            font-size: 12px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass p {\n" +
                            "            margin: 0;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass .hero {\n" +
                            "            background: rgba(204, 31, 48, 1);\n" +
                            "            background: linear-gradient(\n" +
                            "                    45deg,\n" +
                            "                    rgba(123, 53, 132, 1) 0%,\n" +
                            "                    rgba(204, 31, 48, 1) 100%\n" +
                            "            );\n" +
                            "            color: #fff;\n" +
                            "            padding: 30px 0 80px 0;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass .hero:after {\n" +
                            "            content: \"\";\n" +
                            "            clear: both;\n" +
                            "            display: block;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass .hero p {\n" +
                            "            float: left;\n" +
                            "            width: 50%;\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass .hero strong {\n" +
                            "            display: block;\n" +
                            "            font-size: 40px;\n" +
                            "            font-weight: normal;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boarding-pass .hero span {\n" +
                            "            font-size: 12px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boardcard {\n" +
                            "            perspective: 1000;\n" +
                            "            margin: 0 auto;\n" +
                            "            margin-top: -50px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boardcard label {\n" +
                            "            display: block;\n" +
                            "            font-size: 12px;\n" +
                            "            margin-bottom: 2px;\n" +
                            "            color: #969696;\n" +
                            "        }\n" +
                            "\n" +
                            "        .boardcard .row {\n" +
                            "            margin-bottom: 20px;\n" +
                            "        }\n" +
                            "\n" +
                            "        /* .boardcard:hover .card {\n" +
                            "          transform: rotateY(180deg);\n" +
                            "        } */\n" +
                            "\n" +
                            "        .boardcard,\n" +
                            "        .front,\n" +
                            "        .back {\n" +
                            "            width: 280px;\n" +
                            "            height: 330px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .card {\n" +
                            "            transition: 1s;\n" +
                            "            transform-style: preserve-3d;\n" +
                            "            position: relative;\n" +
                            "        }\n" +
                            "\n" +
                            "        .front,\n" +
                            "        .back {\n" +
                            "            backface-visibility: hidden;\n" +
                            "            position: absolute;\n" +
                            "            top: 0;\n" +
                            "            left: 0;\n" +
                            "            padding: 20px;\n" +
                            "            background: #fff;\n" +
                            "            box-shadow: -4px 23px 48px -13px rgba(0, 0, 0, 0.75);\n" +
                            "            border: 1px solid #cc1f2f;\n" +
                            "        }\n" +
                            "\n" +
                            "        .front {\n" +
                            "            z-index: 2;\n" +
                            "            transform: rotateY(0deg);\n" +
                            "        }\n" +
                            "\n" +
                            "        .back {\n" +
                            "            transform: rotateY(180deg);\n" +
                            "        }\n" +
                            "\n" +
                            "        .row:after {\n" +
                            "            content: \"\";\n" +
                            "            clear: both;\n" +
                            "            display: block;\n" +
                            "        }\n" +
                            "\n" +
                            "        .col-50 {\n" +
                            "            width: 50%;\n" +
                            "            float: left;\n" +
                            "        }\n" +
                            "\n" +
                            "        .col-33 {\n" +
                            "            width: 33.33%;\n" +
                            "            float: left;\n" +
                            "        }\n" +
                            "\n" +
                            "        .col-25 {\n" +
                            "            width: 25%;\n" +
                            "            float: left;\n" +
                            "        }\n" +
                            "\n" +
                            "        .col-75 {\n" +
                            "            width: 75%;\n" +
                            "            float: left;\n" +
                            "        }\n" +
                            "\n" +
                            "        .col-50:nth-child(2),\n" +
                            "        .terminal,\n" +
                            "        .gate,\n" +
                            "        .seat {\n" +
                            "            text-align: right;\n" +
                            "        }\n" +
                            "\n" +
                            "        .scanner {\n" +
                            "            text-align: center;\n" +
                            "            padding: 0 0 0 0;\n" +
                            "            margin: -20px 0 0px 0;\n" +
                            "        }\n" +
                            "\n" +
                            "        .qrcode {\n" +
                            "            display: inline-block;\n" +
                            "            width: 120px;\n" +
                            "            position: relative;\n" +
                            "        }\n" +
                            "\n" +
                            "        .hotline {\n" +
                            "            font-size: 12px;\n" +
                            "            text-align: center;\n" +
                            "            position: absolute;\n" +
                            "            left: 0;\n" +
                            "            bottom: 10px;\n" +
                            "            width: 100%;\n" +
                            "            color: #aaa;\n" +
                            "        }\n" +
                            "\n" +
                            "        .back li,\n" +
                            "        .back p {\n" +
                            "            line-height: 1.4;\n" +
                            "            font-size: 13px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .back li {\n" +
                            "            margin-bottom: 5px;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<div class=\"page\">\n" +
                            "    <div class=\"boarding-pass full\">\n" +
                            "        <header>\n" +
                            "            <p\n" +
                            "                    style=\"\n" +
                            "              font-family: Verdana, Geneva, Tahoma, sans-serif;\n" +
                            "              font-size: 20px;\n" +
                            "              font-weight: bold;\n" +
                            "            \"\n" +
                            "            >\n" +
                            "                "+title+"\n" +
                            "            </p>\n" +
                            "        </header>\n" +
                            "        <div class=\"hero\" style=\"height: 5px\">\n" +
                            "            <p class=\"departure\">\n" +
                            "                <strong style=\"font-size: 20px\">" + timeStart + "</strong>\n" +
                            "                <span>Bắt đầu</span>\n" +
                            "            </p>\n" +
                            "            <p class=\"destination\">\n" +
                            "                <strong style=\"font-size: 20px\">" + timeEnd + "</strong>\n" +
                            "                <span>Kết thúc</span>\n" +
                            "            </p>\n" +
                            "        </div>\n" +
                            "        <div class=\"boardcard\">\n" +
                            "            <div  style=\"margin-left: 40px;\"  class=\"flight-info\">\n" +
                            "                <div class=\"row\">\n" +
                            "                    <p class=\"col-50\">\n" +
                            "                        <label>Người đặt</label>\n" +
                            "                        <span style=\"font-size: 15px;\">" + nguoiDat + "</span>\n" +
                            "                    </p>\n" +
                            "                    <p class=\"col-50\">\n" +
                            "                        <label>Thời gian đặt</label>\n" +
                            "                        <span style=\"font-size: 15px;\">" + ngayDat + "</span>\n" +
                            "                    </p>\n" +
                            "                </div>\n" +
                            "                <div class=\"row\">\n" +
                            "                    <p class=\"col-50\">\n" +
                            "                        <label>Ngày check-in</label>\n" +
                            "                        <span style=\"font-size: 15px;\">" + ngayCheckIn + "</span>\n" +
                            "                    </p>\n" +
                            "                    <p class=\"col-50\">\n" +
                            "                        <label>Giá tiền</label>\n" +
                            "                        <span style=\"font-size: 15px;\">" + decimalFormat.format(giaTien) + " VND" + "</span>\n" +
                            "                    </p>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            <br />\n" +
                            "            <div class=\"scanner\">\n" +
                            "                <img src='cid:qrcode' alt='QR Code' />\n" +
                            "            </div>\n" +
                            "            <div class=\"row\">\n" +
                            "                <p style=\"text-align: center; font-size: 12px\">\n" +
                            "                    Quý khách vui lòng giữ lại mã QR code để thực hiện check in tại " +
                            "sân bóng đồng đế. cảm ơn quý khách đã sự dụng dịch vụ!\n" +
                            "                </p>\n" +
                            "            </div>\n" +
                            "            <p class=\"hotline\">Liên hệ? Call 0369 569 225</p>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "</div>\n" +
                            "\n" +
                            "<script\n" +
                            "        src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js\"\n" +
                            "        integrity=\"sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r\"\n" +
                            "        crossorigin=\"anonymous\"\n" +
                            "></script>\n" +
                            "<script\n" +
                            "        src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js\"\n" +
                            "        integrity=\"sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+\"\n" +
                            "        crossorigin=\"anonymous\"\n" +
                            "></script>\n" +
                            "<script\n" +
                            "        src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"\n" +
                            "        integrity=\"sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL\"\n" +
                            "        crossorigin=\"anonymous\"\n" +
                            "></script>\n" +
                            "\n" +
                            "</script>\n" +
                            "</body>\n" +
                            "</html>\n";

                    helper.setText(htmlContent, true); // Chú ý tham số thứ hai là true để cho phép HTML trong nội dung email

                    // Tạo mã QR code và đính kèm vào email
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);
                    File qrCodeFile = new File("qrcode.png");
                    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrCodeFile.toPath());

                    helper.addInline("qrcode", qrCodeFile);

                    mailSender.send(message);

                    // Xoá tệp QR code sau khi gửi email
                    qrCodeFile.delete();
                } catch (MessagingException | IOException | WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return "Send mail run....";
    }
}
