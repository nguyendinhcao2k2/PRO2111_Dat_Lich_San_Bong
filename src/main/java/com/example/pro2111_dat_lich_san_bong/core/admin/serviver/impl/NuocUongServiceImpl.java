package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.excel.NuocUongExportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.excel.NuocUongImportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.NuocUongResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.NuocUongAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.NuocUongService;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.NotFoundException;;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.NuocUongRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;;

/**
 * @author caodinh
 */
@Service
public class NuocUongServiceImpl implements NuocUongService {

    @Autowired
    private NuocUongAdminRepository nuocUongRepository;


    @Override
    public void deleteById(String id) {
        Optional<NuocUong> nuocUong = nuocUongRepository.findById(id);
        if (!nuocUong.isPresent()) {
            throw new NotFoundException("Khong tim thay nuoc uong");
        }
        nuocUongRepository.deleteById(id);
    }

    @Override
    public boolean updateById(String id, NuocUongRequest nuocUongRequest) {
        Optional<NuocUong> nuocUongOptional = nuocUongRepository.findById(id);
        if (!nuocUongOptional.isPresent()) {
            throw new NotFoundException("Khong tim thay nuoc uong");
        }
        try {
            NuocUong nuocUong = nuocUongOptional.get();
            PropertyUtils.copyProperties(nuocUong, nuocUongRequest);
            nuocUongRepository.save(nuocUong);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean save(NuocUongRequest nuocUongRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        NuocUong nuocUong = new NuocUong();
        PropertyUtils.copyProperties(nuocUong, nuocUongRequest);
        nuocUong.setTrangThai(0);
        nuocUongRepository.save(nuocUong);
        return true;
    }

    @Override
    public Page<NuocUongResponse> getNuocUongNyPagaeble(int page, int size) {
        return nuocUongRepository.findAllNuocUong(PageRequest.of(page, size));
    }

    @Override
    public boolean nuocUongImportExcel(MultipartFile file) throws IOException {
        List<NuocUong> nuocUongList = NuocUongImportExcel.readExcel(file);
        try {
            nuocUongRepository.saveAll(nuocUongList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void exprotExcel(HttpServletResponse response) throws IOException {
        NuocUongExportExcel.exportData(response);
    }
}
