package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class HoaDonSanCaUserServiceImpl implements HoaDonSanCaUserService {

    @Autowired
    private HoaDonSanCaUserRepository hoaDonSanCaUserRepository;

    @Override
    public void saveAllHoaDonSanCa(List list) {
        hoaDonSanCaUserRepository.saveAll(list);
    }

    @Override
    @Transactional
    public void saveHoaDonSanCa(HoaDonSanCa hoaDonSanCa) {
        hoaDonSanCaUserRepository.saveAndFlush(hoaDonSanCa);
    }

    @Override
    public void updateAll(List list) {
        try {
            hoaDonSanCaUserRepository.saveAllAndFlush(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List findAllByIdHoaDon(String idHoaDon) {
        return hoaDonSanCaUserRepository.findAllByIdHoaDon(idHoaDon);
    }

    @Override
    @Transactional
    public void deleteAllByIdHoaDon(String idHoaDon) {
        hoaDonSanCaUserRepository.deleteAllByIdHoaDon(idHoaDon);
    }

    @Override
    public HoaDonSanCa findById(String id) {
        try {
            return hoaDonSanCaUserRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteByIdHoaDonSanCa(String id) {
        try {
            hoaDonSanCaUserRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HoaDonSendMailResponse getDetialHoaDon(String idHoaDonSanCa) {
        return hoaDonSanCaUserRepository.getDetialHoaDon(idHoaDonSanCa);
    }

    ;

    @Override
    public HoaDonSanCa findByIdSanCa(String id) {
        try {
            return hoaDonSanCaUserRepository.findHoaDonSanCaByIdSanCa(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HoaDonSendMailResponse> getLisTHDSC(String idHoaDon) {
        return hoaDonSanCaUserRepository.getLisTHDSC(idHoaDon);
    }

    @Override
    public List<HoaDonSanCa> findByIdHoaDon(String idHoaDon) {
        try {
            List<HoaDonSanCa> list = hoaDonSanCaUserRepository.findByIdHoaDon(idHoaDon);
            if (list != null) {
                return list;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
