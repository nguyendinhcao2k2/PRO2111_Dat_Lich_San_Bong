package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thepvph20110
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanCaUserRequest extends PageableRequest {

    String ngayTaoSanCa;
}
