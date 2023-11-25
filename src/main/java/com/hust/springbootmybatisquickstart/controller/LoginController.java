package com.hust.springbootmybatisquickstart.controller;

import com.hust.springbootmybatisquickstart.pojo.Result;
import com.hust.springbootmybatisquickstart.pojo.Trueuser;

import com.hust.springbootmybatisquickstart.service.TrueUserService;
import com.hust.springbootmybatisquickstart.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private TrueUserService trueUserService;

    @PostMapping("/login")
    public Result login(@RequestBody Trueuser trueuser){
        Trueuser e= trueUserService.login(trueuser);
        //登录成功，生成令牌，下发令牌
        if(e!=null){
            Map<String,Object>claims=new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            claims.put("password",e.getPassword());

            String token= JwtUtils.generateJwt(claims);

            String subject = "登录成功通知";
            String body = "您已成功登录系统。";
            String recipientEmail = e.getEmail();

            return Result.success(token);
        }
        //登录失败，放回错误信息
        return  Result.error("用户名或密码错误");
    }

}
