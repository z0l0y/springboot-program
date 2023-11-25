package com.hust.springbootmybatisquickstart.service;

import com.hust.springbootmybatisquickstart.mapper.TrueuserMapper;

import com.hust.springbootmybatisquickstart.pojo.Trueuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class TrueUserServiceImpl implements TrueUserService{
    @Autowired
    private TrueuserMapper trueuserMapper;

    @Override
    public Trueuser login(Trueuser trueuser) {
        String password = trueuser.getPassword();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        // Convert byte array to signum representation
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        trueuser.setPassword(sb.toString());
        Trueuser loginTrueuser = trueuserMapper.getByUsernameAndPassword(trueuser);
        System.out.println(trueuser.getPassword());
        return loginTrueuser;
    }
}
