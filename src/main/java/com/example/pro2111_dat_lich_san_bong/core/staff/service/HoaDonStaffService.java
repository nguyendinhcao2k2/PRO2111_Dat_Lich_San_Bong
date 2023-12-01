package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.CheckInResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author caodinh
 */
public interface HoaDonStaffService {

    List<CheckInResponse> listCheckIn(String param);

    ResponseEntity<?> checkIn(String param);

}