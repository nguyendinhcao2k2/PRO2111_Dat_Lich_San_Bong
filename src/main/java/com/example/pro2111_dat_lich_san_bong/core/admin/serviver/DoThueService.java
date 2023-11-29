package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author caodinh
 */
public interface DoThueService {
    List<DoThue> findAll();

    Page<DoThueResponse> getDoThuePagaeble(int page, int size);

    void deleteById(String id);

    boolean update(DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    boolean save(DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    boolean nuocUongImportExcel(MultipartFile file) throws IOException;

    void exprotExcel(HttpServletResponse response, List<DoThue> doThueList) throws IOException;

    DoThue findById(String id);

    Page<DoThueResponse> findAllByName(int page, int size, String tenDoThue);
}
