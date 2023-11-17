package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class UploadImg {
    public String uploadImg(MultipartFile file) {
        String path = "D:\\PRO2111_DO_AN_TN\\src\\main\\resources\\static\\img";
        File myFile = new File(path);
        if (!myFile.exists()) {
            myFile.mkdirs();
        }
        File saveFile = null;
        try {
            saveFile = new File(myFile, file.getOriginalFilename());
            file.transferTo(saveFile);
            return "Upload image thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload image thất bại!";
        }
    }
}
