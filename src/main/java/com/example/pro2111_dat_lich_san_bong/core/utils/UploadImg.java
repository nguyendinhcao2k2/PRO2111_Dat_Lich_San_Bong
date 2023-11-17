package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImg {
    @Value("${pathUpload_Img}")
    private String path;

    public String uploadImg(MultipartFile file) throws IOException {
//        String path = "D:\\PRO2111_DO_AN_TN\\src\\main\\resources\\static\\img";
        //C1:
//        File myFile = new File(path);
//        if (!myFile.exists()) {
//            myFile.mkdirs();
//        }
//        File saveFile = null;
//C2:
        try {
            //them
            Path filePath = Paths.get(path, file.getOriginalFilename());
            File saveFile = filePath.toFile();

            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            saveFile = new File(path, file.getOriginalFilename());
            // them
            file.transferTo(saveFile);
            return "Upload image thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload image thất bại!";
        }
//C3:
//        String fileName = file.getOriginalFilename();
//        String filePath = Paths.get(path,fileName).toString();
//        File dest = new File(filePath);
//        file.transferTo(dest);
//        return "OKE";
    }
}
