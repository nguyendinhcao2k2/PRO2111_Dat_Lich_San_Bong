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
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<DoThue> findAll() {
        try {
            return doThueAdminRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<DoThueResponse> getDoThuePagaeble(int page, int size) {
        try {
            return doThueAdminRepository.findAllDoThue(PageRequest.of(page, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
    public boolean update(DoThueRequest doThueRequest) {
        try {
            DoThue doThue = mapper.map(doThueRequest, DoThue.class);
            doThueAdminRepository.saveAndFlush(doThue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean save(DoThueRequest doThueRequest) {
        try {
            if (doThueRequest != null) {
                DoThue doThue = mapper.map(doThueRequest, DoThue.class);
                doThue.setTrangThai(0);
                doThueAdminRepository.save(doThue);
                return true;
            }
            return false;
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
    public void exprotExcel(HttpServletResponse response, List<DoThue> doThueList) throws IOException {
        try {
            DoThueExportExcel.exportData(response, doThueList);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DoThue findById(String id) {
        try {
            return doThueAdminRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<DoThueResponse> findAllByName(int page, int size, String tenDoThue) {
        try {
            return doThueAdminRepository.findAllByName(PageRequest.of(page, size), tenDoThue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
