package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.excel.DoThueExportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.excel.DoThueImportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.excel.NuocUongImportExcel;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.DoThueAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DoThueService;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author caodinh
 */
@Service
public class DoThueServiceImpl implements DoThueService {

    @Autowired
    private DoThueAdminRepository doThueAdminRepository;

    @Override
    public Page<DoThueResponse> getDoThuePagaeble(int page, int size) {
        return doThueAdminRepository.findAllDoThue(PageRequest.of(page, size));
    }

    @Override
    public void deleteById(String id) {
        Optional<DoThue> nuocUong = doThueAdminRepository.findById(id);
        if (!nuocUong.isPresent()) {
            throw new NotFoundException("Khong tim thay nuoc uong");
        }
        doThueAdminRepository.deleteById(id);
    }

    @Override
    public boolean updateById(String id, DoThueRequest doThueRequest) {
        Optional<DoThue> doThueOptional = doThueAdminRepository.findById(id);
        if (!doThueOptional.isPresent()) {
            throw new NotFoundException("Khong tim thay nuoc uong");
        }
        try {
            DoThue doThue = doThueOptional.get();
            PropertyUtils.copyProperties(doThue, doThueRequest);
            doThueAdminRepository.save(doThue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean save(DoThueRequest doThueRequest) {
        try {
            DoThue doThue = new DoThue();
            PropertyUtils.copyProperties(doThue, doThueRequest);
            doThue.setTrangThai(0);
            doThueAdminRepository.save(doThue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean nuocUongImportExcel(MultipartFile file) throws IOException {
        List<DoThue> doThueList = DoThueImportExcel.readExcel(file);
        try {
            doThueAdminRepository.saveAll(doThueList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void exprotExcel(HttpServletResponse response) throws IOException {
        DoThueExportExcel.exportData(response);
    }
}
