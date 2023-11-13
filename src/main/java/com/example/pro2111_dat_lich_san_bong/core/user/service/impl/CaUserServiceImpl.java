package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaDoiLichUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.CaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class CaUserServiceImpl implements CaUserService {

    @Autowired
    private CaUserRepository caUserRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public List getAllCaByIdLoaiSan(String idLoaiSan) {
        return caUserRepository.getAllCaByIdLoaiSan(idLoaiSan);
    }

    @Override
    public CaUserResponse getCaByIdCa(String idCa, String idLoaiSan) {
        return caUserRepository.getCaByIdCa(idCa,idLoaiSan);
    }

    @Override
    public List<CaDoiLichUserResponse> findAll() {
        try {
            TypeToken<List<CaDoiLichUserResponse>> token = new TypeToken<>() {
            };
            return mapper.map(caUserRepository.findAll(Sort.by("tenCa").ascending()), token.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ca findFirstByTenCa(String tenCa) {
        try {
            return caUserRepository.findFirstByTenCa(tenCa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
