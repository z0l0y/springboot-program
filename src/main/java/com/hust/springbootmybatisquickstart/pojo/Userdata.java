package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//Lombok的方法，就是不用你自己写构造器了
@NoArgsConstructor
@AllArgsConstructor
public class Userdata {
    private Integer index;
    private String name;
    private String id;
    private String phone;
    private String address;
}
