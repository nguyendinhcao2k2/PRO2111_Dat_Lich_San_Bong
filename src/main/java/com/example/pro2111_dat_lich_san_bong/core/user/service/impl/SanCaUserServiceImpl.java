package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.SanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.ThoiGianDLUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiThoiGianDL;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thepvph20110
 */
@Service
public class SanCaUserServiceImpl implements SanCaUserService {

    @Autowired
    private SanCaUserRepository sanCaUserRepository;

    @Autowired
    private ThoiGianDLUserRepository thoiGianDLUserRepository;

    @Autowired
    private SanBongUserService sanBongUserService;


    @Override
    public List getAllSanCa(EventRequest request) {
        List<SanCaUserResponse> listSanCa = sanCaUserRepository.getAllSanCa(request);
       /* List<SanCaUserResponse> listTGDatLich = thoiGianDLUserRepository.getAllTGDLByUserId(request, TrangThaiThoiGianDL.CHO_XAC_NHAN.ordinal());
        if(listTGDatLich != null || !listTGDatLich.isEmpty()){
            listSanCa.addAll(listTGDatLich);
        }*/
        return listSanCa;
    }

    @Override
    public int checkSanCa(String idLoaiSan, String idCa, String ngayDat) {

        String[] dateFomat = ngayDat.split("-");

        String textCheck = idCa + "+" + idLoaiSan + "+" + dateFomat[2] + dateFomat[1] + dateFomat[0];
        int countSanCa = sanCaUserRepository.getAllSanCa(textCheck);
        int countSanBong = sanBongUserService.countSanBong(idLoaiSan);

        if (countSanCa < countSanBong) {
            return 0;
        }
        return 1;
    }

    @Override
    public List checkListSanCa(List<CheckSanCaUserRequest> list) {
        //list trả ra những san ca đã có người đặt
        List<CheckSanCaUserRequest> listExist = new ArrayList<>();
        Map<String, Integer> mapCoutnExist = new HashMap<>();
        for (CheckSanCaUserRequest request : list) {
            String[] dateFomat = request.getNgayDat().split("-");

            //đếm số lượng sân bóng theo id loại san
            int countSanBong = sanBongUserService.countSanBong(request.getIdLoaiSan());

            // id của sân ca
            String textCheck = request.getIdCa() + "+" + request.getIdLoaiSan() + "+" + dateFomat[2] + dateFomat[1] + dateFomat[0];
            //kiểm tra xem có trong map không
            if (!mapCoutnExist.containsKey(textCheck)) { // nếu không có trong map
                int countSanCa = sanCaUserRepository.getAllSanCa(textCheck);

                //kiểm tra xem số lương san ca trong db có còn không
                if (countSanCa < countSanBong) { //nếu san ca trong db có bé hơn số lượng sân bóng
                    mapCoutnExist.put(textCheck, countSanCa + 1); //thì put vào map số lương san ca trong db và +1 san ca muốn đặt

                } else {
                    listExist.add(request);
                }
            } else { //nếu trong map đã tồn tại
                int allCountSanCaInList = mapCoutnExist.get(textCheck);
                // kiểm tra xem trong map (đã lơn hoặc) bằng số lượng sân bóng chưa
                if (allCountSanCaInList >= countSanBong) { //đã (lớn hơn hoặc) bằng rồi thì thêm vào list đã tồn tại
                    listExist.add(request);
                } else {
                    //đếm số sân ca trong db
                    int countSanCa = sanCaUserRepository.getAllSanCa(textCheck);

                    //lấy số sân ca muốn đặt trước đó bằng cách lấy số sân ca trong map trừ đi sân ca trong db
                    int soDuocCongTruocDo = allCountSanCaInList - countSanCa;

                    //kiểm tra xem sân ca được cổng + cho sân ca trong db+ 1 sân ca muốn đặt có bé hơn số sân bóng không
                    if (countSanCa + soDuocCongTruocDo + 1 <= countSanBong) { // nếu bé hơn số lượng sân bóng
                        mapCoutnExist.put(textCheck, allCountSanCaInList + 1);// thì cập nhật lại số lượng san ca muốn đặt+ sân ca trong db

                    } else {
                        listExist.add(request);
                    }
                }
            }

        }

        return listExist;
    }

    @Override
    public void saveAllSanCa(List list) {
        sanCaUserRepository.saveAll(list);
    }

    @Override
    public SanCa findSanCaById(String id) {
        return sanCaUserRepository.findAllById(id);
    }

    @Override
    @Transactional
    public void deleteSanCaById(String idSanCa) {
        try {
            sanCaUserRepository.deleteSanCaById(idSanCa);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void saveSanCa(SanCa sanCa) {
        try {
            sanCaUserRepository.save(sanCa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
