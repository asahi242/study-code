package com.demo.tkmybatis.mapper;

import com.demo.tkmybatis.entity.UserInfo;
import com.demo.tkmybatis.entity.dto.UserDto;
import com.demo.tkmybatis.entity.vo.UserVo;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Component
public interface UserInfoMapper extends Mapper<UserInfo> {
    List<UserInfo> getByAccount(String account);
    UserDto getUserByName(UserVo userVo);
}

