package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/v1/admin/ca")
public class CaController {
    @Autowired
    private CaRepository caRepository;

    @GetMapping("/get-all")
    public String getAll(Model model) {
        List<Ca> listCas = caRepository.findAll();
        Collections.sort(listCas, (listCas1, listCas2) -> listCas1.getThoiGianBatDau().compareTo(listCas2.getThoiGianBatDau()));
        model.addAttribute("listCas", listCas);
        return "owner/quan-ly-ca";
    }
    @PostMapping("/save")
    public String save(Model model, @ModelAttribute("ca") Ca ca){
        System.out.println(ca.toString());
        return "owner/quan-ly-ca";
    }
    @GetMapping("/san")
    public String getSan(Model model) {
        return "owner/quan-ly-san";
    }
}
