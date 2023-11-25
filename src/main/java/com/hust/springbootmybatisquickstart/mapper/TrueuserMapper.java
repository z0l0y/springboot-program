package com.hust.springbootmybatisquickstart.mapper;

import com.hust.springbootmybatisquickstart.pojo.Trueuser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface TrueuserMapper {

    @Select("select * from users where username=#{username} and password=#{password}")
    public Trueuser getByUsernameAndPassword(Trueuser trueuser);

}
