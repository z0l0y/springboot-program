package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private String index;
    private String name;
    private String ladingId;
    private String userPhone;
    private String cargoName;
    private String cargoWeight;
    private String containerId;
    private String logisticsCom;
    private String linkman;
    private String linkmanPhone;
}
