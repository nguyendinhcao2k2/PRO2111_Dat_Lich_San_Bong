package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckTimeListReQuest {
    private Time timeStart;
    private Time timeEnd;
}
