package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuChat;
import com.example.pro2111_dat_lich_san_bong.model.request.LichSuChatRequest;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.repository.LichSuChatRepository;
import com.example.pro2111_dat_lich_san_bong.service.LichSuChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuChatServiceImpl implements LichSuChatService {

    @Autowired
    private LichSuChatRepository lichSuChatRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<LichSuChat> getAll() {
        return lichSuChatRepository.findAll();
    }

    @Override
    public LichSuChat findById(String id) {
        return lichSuChatRepository.findById(id).get();
    }

    @Override
    public LichSuChat create(LichSuChatRequest lichSuChatRequest) {
        LichSuChat lichSuChat = new LichSuChat(null, lichSuChatRequest.getNoiDung(),
                lichSuChatRequest.getThoiGianTao(),lichSuChatRequest.getIdAccount());
        return lichSuChatRepository.save(lichSuChat);
    }

    @Override
    public void remove(String id) {
        lichSuChatRepository.deleteById(id);
    }

    @Override
    public LichSuChat update(String id, LichSuChatRequest lichSuChatRequest) {
        LichSuChat lichSuChat = lichSuChatRepository.findById(id).get();
        lichSuChat.setNoiDung(lichSuChatRequest.getNoiDung());
        lichSuChat.setThoiGianTao(lichSuChatRequest.getThoiGianTao());
        lichSuChat.setIdAccount(lichSuChatRequest.getIdAccount());
        return lichSuChatRepository.save(lichSuChat);
    }
}
