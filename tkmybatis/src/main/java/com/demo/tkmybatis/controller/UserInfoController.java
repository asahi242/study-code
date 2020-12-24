package com.demo.tkmybatis.controller;

import com.demo.tkmybatis.entity.UserInfo;
import com.demo.tkmybatis.entity.dto.UserDto;
import com.demo.tkmybatis.entity.vo.UserVo;
import com.demo.tkmybatis.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tkmybatis")
public class UserInfoController {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @PostMapping("/saveUser")
    public boolean saveUser(@RequestBody UserInfo userInfo){
        boolean result = false;
        int insert = userInfoMapper.insert(userInfo);

        if (insert>0){
            result = true;
        }
        return result;
    }
    @PostMapping("/getByAccount")
    public List<UserInfo> getByAccount(String account){

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo(account);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos;
    }
    @PostMapping("/getByAccount1")
    public List<UserInfo> getByAccount1(@RequestBody HashMap<String,Object> map){
        String account = (String) map.get("account");
        List<UserInfo> userInfos = userInfoMapper.getByAccount(account);
        return userInfos;
    }
    @PostMapping("/getUserByAccount")
    public UserDto getUserByAccount(@RequestBody UserVo userVo){
        UserDto userDto = userInfoMapper.getUserByName(userVo);
        return userDto;
    }
}
