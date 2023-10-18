package com.example.pro2111_dat_lich_san_bong.core.common.base;

import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thepvph20110
 */

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int size = PaginationConstant.DEFAULT_SIZE;
}
