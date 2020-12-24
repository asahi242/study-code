package com.asahi.demo.springSecurity.controller;

import com.asahi.demo.ehcache.EhcacheUtils;
import com.asahi.demo.springSecurity.model.AuthUser;
import com.asahi.demo.springSecurity.repository.UserRepository;
import com.asahi.demo.springSecurity.utils.AuditorAwareImpl;
import com.asahi.demo.springSecurity.utils.MessageUtils;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login/error")
    public String loginError(HttpServletRequest request, Map<String,Object> map){
        map.put("flag", MessageUtils.getMsg());
        return "login";
    }
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/saveUser")
    public String saveUser(Model model, AuthUser auther){

        AuthUser save = userRepository.save(auther);
        if(save!=null){
            model.addAttribute("flag","用户"+save.getUsername()+"添加成功");
        }
        AuthUser user = (AuthUser) EhcacheUtils.getEleObject("user");
        model.addAttribute("user",user);
        return "qiantai";
    }
    @PostMapping("/updateUser")
    public String updateUser(Model model,AuthUser authUser){
        Integer i = userRepository.updateById(authUser.getId(), authUser.getUsername(), authUser.getPassword(),authUser.getUsername(),new Date());
        EhcacheUtils.setElement("user",authUser);
        new AuditorAwareImpl().getCurrentAuditor();
        model.addAttribute("user",authUser);
        return "qiantai";
    }
    @GetMapping("/qiantai")
    public String qiantai(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = userRepository.getByUsername(authentication.getName());
        model.addAttribute("user",authUser);
        EhcacheUtils.setElement("user",authUser);
        return "qiantai";
    }

}
