package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuChat;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.request.LichSuChatRequest;
import com.example.pro2111_dat_lich_san_bong.model.request.LoaiSanRequest;

import java.util.List;

public interface LichSuChatService {
    List<LichSuChat> getAll();

    LichSuChat findById(String id);

    LichSuChat create(LichSuChatRequest lichSuChatRequest);

    void remove(String id);

    LichSuChat update(String id, LichSuChatRequest lichSuChatRequest);
}
