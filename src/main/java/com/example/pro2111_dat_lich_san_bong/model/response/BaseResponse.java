package com.example.pro2111_dat_lich_san_bong.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author caodinh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<D>{

    private HttpStatus statusCode;
    private D content;

}
