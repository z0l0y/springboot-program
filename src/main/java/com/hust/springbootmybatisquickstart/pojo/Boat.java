package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boat {
    private String shipCompany;
    private String shipName;
    private String cargoName;
    private String ownerName;
    private String linkman;
    private String name;
    private String phone;
}
