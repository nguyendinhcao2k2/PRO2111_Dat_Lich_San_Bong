package com.example.pro2111_dat_lich_san_bong.core.authen.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author caodinh
 */
@Getter
@Setter
@ToString
public class AccountRequest {

    private String account;
    private String displayName;
    private String phoneNumber;
    private String password;
    private String rePassword;

}
