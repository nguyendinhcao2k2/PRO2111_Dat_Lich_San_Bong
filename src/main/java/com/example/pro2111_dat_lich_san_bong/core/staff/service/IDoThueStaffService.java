package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IDoThueStaffService {
    List<DoThue> getDoThuePagaeble();

    DoThue getOneDoThue(String id);

    void deleteById(String id);

    boolean updateById(String id, DoThue doThue);

    boolean save(DoThue doThue) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
