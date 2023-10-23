package com.example.pro2111_dat_lich_san_bong.core.common.base;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author thepvph20110
 */

@Getter
public class PageableObject<T> {

    private List<T> data;
    private long totalPages;
    private int currentPage;
    private int pageSize;

    public PageableObject(Page<T> page) {
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
    }
}
