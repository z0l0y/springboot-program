package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trueuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*用户信息包括邮箱，密码，昵称等，可自定义。用户密码加密存储。*/
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String superUsers;
}

