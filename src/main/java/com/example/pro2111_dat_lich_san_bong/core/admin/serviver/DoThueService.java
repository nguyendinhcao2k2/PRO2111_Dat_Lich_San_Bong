package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.DoThueRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author caodinh
 */
public interface DoThueService {

    Page<DoThueResponse> getDoThuePagaeble(int page, int size);

    void deleteById(String id);

    boolean updateById(String id, DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    boolean save(DoThueRequest doThueRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    boolean nuocUongImportExcel(MultipartFile file) throws IOException;

    void exprotExcel(HttpServletResponse response) throws IOException;
}
