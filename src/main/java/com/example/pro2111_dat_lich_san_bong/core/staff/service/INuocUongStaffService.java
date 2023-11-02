package com.example.pro2111_dat_lich_san_bong.core.staff.service;


import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface INuocUongStaffService {
    List<NuocUong> getNuocUongPagaeble();

    NuocUong getOneNuocUong(String id);

    void deleteById(String id);

    boolean updateById(String id, NuocUong nuocUong);

    boolean save(NuocUong nuocUong) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

}
