package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.NuocUongResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.NuocUongRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author caodinh
 */
public interface NuocUongService {

    void deleteById(String id);

    boolean updateById(String id, NuocUongRequest nuocUongRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    boolean save(NuocUongRequest nuocUongRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    Page<NuocUongResponse> getNuocUongNyPagaeble(int page, int size);

    boolean nuocUongImportExcel(MultipartFile file) throws IOException;

    void exprotExcel(HttpServletResponse response) throws IOException;
}
